package com.lfy.mallware.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallware.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-17 18:13:46
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

