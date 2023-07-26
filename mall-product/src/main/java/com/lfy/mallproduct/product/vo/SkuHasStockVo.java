package com.lfy.mallproduct.product.vo;

import lombok.Data;

/**
 * @Author FeiYang
 * @Date 7/24/2023 4:20 PM
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private boolean hasStock;
}
