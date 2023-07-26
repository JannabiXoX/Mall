package com.lfy.mallware.ware.vo;/*
 *@Author:user
 *@Date:7/18/2023 9:59 AM
 */

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Slf4j
public class PurchaseDoneVo {
    @NotNull
    private Long id;//采购单id

    private List<PurchaseItemDoneVo> items;

}
