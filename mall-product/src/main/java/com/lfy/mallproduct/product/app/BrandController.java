package com.lfy.mallproduct.product.app;

import java.util.Arrays;
import java.util.Map;

import com.lfy.common.valid.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lfy.mallproduct.product.entity.BrandEntity;
import com.lfy.mallproduct.product.service.BrandService;
import com.lfy.common.utils.PageUtils;
import com.lfy.common.utils.R;


/**
 * 品牌
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-04 22:24:51
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand /* , BindingResult bindingResult*/) {
//        if (bindingResult.hasErrors()) {
//            //存储错误信息
//            Map<String, String> map = new HashMap<>();
//            //获取所有字段的错误信息
//            bindingResult.getFieldErrors().forEach((item) -> {
//                String message = item.getDefaultMessage();
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交数据不合法").put("data", map);
//        } else {
        brandService.save(brand);

        return R.ok();

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateShowStatus(@RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
