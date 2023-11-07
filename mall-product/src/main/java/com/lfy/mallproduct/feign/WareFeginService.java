package com.lfy.mallproduct.feign;/*
 *@Author:user
 *@Date:7/23/2023 11:23 AM
 */

import com.lfy.common.utils.R;
import com.lfy.mallproduct.product.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mall-ware")
public interface WareFeginService {
    @PostMapping("/ware/waresku/hasStock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);
}
