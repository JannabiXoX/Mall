package com.lfy.mallcoupon.coupon.dao;

import com.lfy.mallcoupon.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-05 09:55:24
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
