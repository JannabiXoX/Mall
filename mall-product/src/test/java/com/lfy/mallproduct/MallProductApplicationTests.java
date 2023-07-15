package com.lfy.mallproduct;


import com.lfy.mallproduct.product.entity.BrandEntity;
import com.lfy.mallproduct.product.service.BrandService;
import com.lfy.mallproduct.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testFindPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225l);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("huawei");
        brandService.save(brandEntity);
        System.out.println("保存成功");
    }

}
