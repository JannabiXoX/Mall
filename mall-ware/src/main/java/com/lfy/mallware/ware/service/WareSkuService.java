package com.lfy.mallware.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallware.ware.entity.WareSkuEntity;
import com.lfy.mallware.ware.vo.SkuHasStockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-17 18:13:46
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);


    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);
}

