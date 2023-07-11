package com.lfy.mallorder.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallorder.order.entity.OmsOrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-05 10:14:49
 */
public interface OmsOrderService extends IService<OmsOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

