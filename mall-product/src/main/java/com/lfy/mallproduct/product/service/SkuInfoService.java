package com.lfy.mallproduct.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallproduct.product.entity.SkuInfoEntity;
import com.lfy.mallproduct.product.entity.SpuInfoEntity;
import com.lfy.mallproduct.product.vo.SpuSaveVo;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:50
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);
}

