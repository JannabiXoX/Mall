package com.lfy.mallproduct.product.dao;

import com.lfy.mallproduct.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:50
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId,@Param("name") String name);
}
