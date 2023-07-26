package com.lfy.mallmember.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfy.common.utils.PageUtils;
import com.lfy.mallmember.member.entity.MemberReceiveAddressEntity;

import java.util.Map;

/**
 * 会员收货地址
 *
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-16 11:53:06
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

