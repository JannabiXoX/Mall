package com.lfy.mallproduct;


import com.lfy.mallproduct.product.entity.BrandEntity;
import com.lfy.mallproduct.product.service.BrandService;
import com.lfy.mallproduct.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;


    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testFindPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225l);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }

    @Test
    public void initRedis() {
        redisTemplate.opsForValue().set("hello", "world");
    }

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("huawei");
        brandService.save(brandEntity);
        System.out.println("保存成功");
    }

}
