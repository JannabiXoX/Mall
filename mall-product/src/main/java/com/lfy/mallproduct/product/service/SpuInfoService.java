package com.lfy.mallproduct.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallproduct.product.entity.SpuInfoDescEntity;
import com.lfy.mallproduct.product.entity.SpuInfoEntity;
import com.lfy.mallproduct.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:50
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);
}

