package com.lfy.mallmember.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallmember.member.entity.UmsMemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-05 10:05:38
 */
public interface UmsMemberService extends IService<UmsMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

