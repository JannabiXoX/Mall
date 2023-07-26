/**
  * Copyright 2023 bejson.com
  */
package com.lfy.mallproduct.product.vo;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-07-16 21:29:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private double weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;

}
