package com.lfy.mallware.ware.vo;/*
 *@Author:user
 *@Date:7/18/2023 10:00 AM
 */

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Data
@Slf4j
public class PurchaseItemDoneVo {
    @NotNull
    private Long itemId;

    private int status;

    private String reason;
}
