package com.lfy.mallware.feign;/*
 *@Author:user
 *@Date:7/18/2023 10:57 AM
 */

import com.lfy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-product")
public interface ProductFeignService {

    /**
     * /product/spuinfo/info/{skuId}
     * /api/product/spuinfo/info/{skuId}
     * 1)、让所有请求过网关;
     *   1、@FeignClient( "mall-gateway"): 给mall-gateway所在的机器发请求
     *   2、/api/product/spuinfo/info/{skuId}
     * 2)、直接让后台指定服务处理
     *   1、@FeignClient（”mall-product")
     *   2、/product/spuinfo/info/{skuId}
     * @param skuId
     * @return
     */
    @RequestMapping("/product/spuinfo/info/{skuId}")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("skuId") Long skuId);
}
