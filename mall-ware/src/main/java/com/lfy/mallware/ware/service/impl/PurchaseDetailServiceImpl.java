package com.lfy.mallware.ware.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfy.common.utils.PageUtils;
import com.lfy.common.utils.Query;

import com.lfy.mallware.ware.dao.PurchaseDetailDao;
import com.lfy.mallware.ware.entity.PurchaseDetailEntity;
import com.lfy.mallware.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        String key = (String) params.get("key");
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("purchase_id",key)
                    .or().eq("sku_id",key);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailPurchaseId(Long id) {
        List<PurchaseDetailEntity> purchase_id = this.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", id));

        return purchase_id;
    }

}
