package com.lfy.mallware.ware.vo;/*
 *@Author:user
 *@Date:7/17/2023 9:54 PM
 */

import lombok.Data;

import java.util.List;

@Data
public class MergeVo {

    private Long purchaseId;

    private List<Long> items;
}
