package com.lfy.mallproduct.product.dao;

import com.lfy.mallproduct.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:50
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
