package com.lfy.mallcoupon.coupon.dao;

import com.lfy.mallcoupon.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-05 09:55:24
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
