package com.lfy.mallsearch.cotroller;

import com.lfy.common.exception.BizCodeEnume;
import com.lfy.common.to.es.SkuEsModel;
import com.lfy.common.utils.R;
import com.lfy.mallsearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:feiyang
 * @Date:7/24/2023 9:46 PM
 */

@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStartUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean b = false;
        try {
            b = productSaveService.productStartUp(skuEsModels);
        } catch (Exception e) {
            log.error("ElasticSaveController商品上架错误", e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXEPTION.getMsg());
        }
        if (b) {
            return R.ok();
        } else {
            return R.error(BizCodeEnume.PRODUCT_UP_EXEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXEPTION.getMsg());
        }
    }
}
