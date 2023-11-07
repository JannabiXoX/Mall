package com.lfy.mallproduct.product.web;

import com.lfy.mallproduct.product.entity.CategoryEntity;
import com.lfy.mallproduct.product.service.CategoryService;
import com.lfy.mallproduct.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author:feiyang
 * @Date:7/27/2023 10:38 AM
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;
    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        //TODO 查出所有分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();

        model.addAttribute("categories",categoryEntities);
        return "index";
    }

    //index/json/catalog.json
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }
}
