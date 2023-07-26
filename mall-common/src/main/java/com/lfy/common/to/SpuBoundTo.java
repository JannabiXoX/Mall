package com.lfy.common.to;/*
 *@Author:user
 *@Date:7/17/2023 11:01 AM
 */

import lombok.Data;

import java.math.BigDecimal;
@Data
public class SpuBoundTo {

    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;

}
