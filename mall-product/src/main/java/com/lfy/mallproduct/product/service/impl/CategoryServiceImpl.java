package com.lfy.mallproduct.product.service.impl;

import com.lfy.mallproduct.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfy.common.utils.PageUtils;
import com.lfy.common.utils.Query;

import com.lfy.mallproduct.product.dao.CategoryDao;
import com.lfy.mallproduct.product.entity.CategoryEntity;
import com.lfy.mallproduct.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

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
        List<Long> parentPath = findParentPath(catelogId,paths);
        Collections.reverse(parentPath);
        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联数据
     * @param category
     */
    @Override
    public void updateCasecade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
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
