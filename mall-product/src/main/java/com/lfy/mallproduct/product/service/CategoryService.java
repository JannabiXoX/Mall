package com.lfy.mallproduct.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallproduct.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:50
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    /**
     * 找到catelogId的完整路径
     * 【父/子/孙】
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    void updateCasecade(CategoryEntity category);
}

