package com.lfy.mallproduct.product.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lfy.mallproduct.product.service.CategoryBrandRelationService;
import com.lfy.mallproduct.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfy.common.utils.PageUtils;
import com.lfy.common.utils.Query;

import com.lfy.mallproduct.product.dao.CategoryDao;
import com.lfy.mallproduct.product.entity.CategoryEntity;
import com.lfy.mallproduct.product.service.CategoryService;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    private Map<String, Object> cache = new HashMap<>();

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //组装父子树形结构
        //找一级分类
        List<CategoryEntity> parentLevelMenus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            menu.setChildren(getChild(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return parentLevelMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1.检查当前删除的菜单是否在其他地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    //    [2,25,225]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联数据
     *
     * @param category
     */
    @Override
    public void updateCasecade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return entities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        //
        // 1.空结果缓存--解决缓存穿透
        // 2.设置过期时间--解决缓存雪崩
        // 3.加锁--解决缓存击穿
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            System.out.println("缓存未命中...查询数据库...");
            Map<String, List<Catelog2Vo>> catalogJSONFromDB = getCatalogJsonFromDBWithRedisLock();
            return catalogJSONFromDB;
        }
        System.out.println("缓存命中...直接返回...");
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return result;
    }

    //TODO 产生堆外内存移除，OutOfDirectMemoryError
    // 1）、reids lettuce 导致内存移除
    // 解决方案 1.升级lettuce客户端 2.使用jedis


    /**
     * 如何保证数据一致性
     * 1.双写模式
     * 2.失效模式
     * 一致性解决方案
     * 缓存所有数据都有过期时间，数据过期下次查询触发主动更新
     * 读写数据的时候，加上分布式的读写锁
     * 经常写经常读
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithRedissonLock() {

        // 占分布式锁
        // 锁的粒度，具体到缓存的是某个数据 如 product-11-lock
        RLock lock = redisson.getLock("catalogJson-Lock");
        lock.lock();
        Map<String, List<Catelog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock.unlock();
        }
        return dataFromDb;

    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithRedisLock() {

        String uuid = UUID.randomUUID().toString();
        // 占分布式锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock) {
            // 加锁成功...执行业务
            // 设置过期时间
            System.out.println("获取分布式锁成功");
            Map<String, List<Catelog2Vo>> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class)
                        , Arrays.asList("lock")
                        , uuid);
            }
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(lockValue)) {
//                redisTemplate.delete("lock");
//            }
            return dataFromDb;
        } else {
            // 加锁失败...重试
            // 休眠一百毫秒重师
            System.out.println("获取分布式锁失败重试");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                log.error("线程超时问题");
            }
            return getCatalogJsonFromDBWithRedisLock();

        }


    }

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        // 得到锁后 再去缓存中确定依次，如果没有才需要继续查询
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (!StringUtils.isEmpty(catalogJSON)) {
            // 缓存不为null 直接返回
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return result;
        }
        System.out.println("查询了数据库。。。");
        //查出所有1级分类
        List<CategoryEntity> level1Categorys = getLevel1Categorys();
        //查出封装数据
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //查到一级分类后查一级分类的二级分类
            List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
            //封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null) {
                catelog2Vos = entities.stream().map(level2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, level2.getCatId().toString(), level2.getName());
                    //查找三级分类
                    List<CategoryEntity> level3Catalog = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", level2.getCatId()));
                    if (level3Catalog != null) {
                        List<Catelog2Vo.Category3Vo> collect = level3Catalog.stream().map(level3 -> {
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(level2.getCatId().toString(), level3.getCatId().toString(), level3.getName());
                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        String traStr = JSON.toJSONString(parent_cid);
        redisTemplate.opsForValue().set("catalogJSON", traStr, 1, TimeUnit.DAYS);
        return parent_cid;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithLocalLock() {

//        //  缓存中有直接使用缓存中的数据
//        Map<String, List<Catelog2Vo>> catalogJson = (Map<String, List<Catelog2Vo>>) cache.get("catalogJson");
//        if (cache.get("catalogJson") == null) {
//
//        }
//        return catalogJson;

        synchronized (this) {
            // 得到锁后 再去缓存中确定依次，如果没有才需要继续查询
            String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
            if (!StringUtils.isEmpty(catalogJSON)) {
                // 缓存不为null 直接返回
                Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
                });
                return result;
            }
            System.out.println("查询了数据库。。。");
            //查出所有1级分类
            List<CategoryEntity> level1Categorys = getLevel1Categorys();
            //查出封装数据
            Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                //查到一级分类后查一级分类的二级分类
                List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
                //封装上面的结果
                List<Catelog2Vo> catelog2Vos = null;
                if (entities != null) {
                    catelog2Vos = entities.stream().map(level2 -> {
                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, level2.getCatId().toString(), level2.getName());
                        //查找三级分类
                        List<CategoryEntity> level3Catalog = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", level2.getCatId()));
                        if (level3Catalog != null) {
                            List<Catelog2Vo.Category3Vo> collect = level3Catalog.stream().map(level3 -> {
                                Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(level2.getCatId().toString(), level3.getCatId().toString(), level3.getName());
                                return category3Vo;
                            }).collect(Collectors.toList());
                            catelog2Vo.setCatalog3List(collect);
                        }
                        return catelog2Vo;
                    }).collect(Collectors.toList());
                }

                return catelog2Vos;
            }));

            String traStr = JSON.toJSONString(parent_cid);
            redisTemplate.opsForValue().set("catalogJSON", traStr, 1, TimeUnit.DAYS);
            return parent_cid;
        }

    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    //递归查找子分类
    public List<CategoryEntity> getChild(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> childLevelMenus = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //找子菜单
            categoryEntity.setChildren(getChild(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            //子菜单排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return childLevelMenus;
    }

}
