package com.lfy.mallproduct.feign;

import com.lfy.common.to.es.SkuEsModel;
import com.lfy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author:feiyang
 * @Date:7/24/2023 10:44 PM
 */
//远程调用search服务
@FeignClient("mall-search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    R productStartUp(@RequestBody List<SkuEsModel> skuEsModels);

}
