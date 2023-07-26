package com.lfy.mallware.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallware.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-17 18:13:46
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

