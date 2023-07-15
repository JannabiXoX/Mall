package com.lfy.mallproduct.product.service.impl;

import com.lfy.mallproduct.product.dao.BrandDao;
import com.lfy.mallproduct.product.dao.CategoryDao;
import com.lfy.mallproduct.product.entity.BrandEntity;
import com.lfy.mallproduct.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfy.common.utils.PageUtils;
import com.lfy.common.utils.Query;

import com.lfy.mallproduct.product.dao.CategoryBrandRelationDao;
import com.lfy.mallproduct.product.entity.CategoryBrandRelationEntity;
import com.lfy.mallproduct.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandDao brandDao;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {

        Long catelogId = categoryBrandRelation.getCatelogId();
        Long brandId = categoryBrandRelation.getBrandId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

        categoryBrandRelation.setBrandName(brandEntity.getName());

        categoryBrandRelation.setCatelogName(categoryEntity.getName());

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();

        relationEntity.setBrandId(brandId);

        relationEntity.setBrandName(name);
        this.update(relationEntity, new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId, name);
    }

}
