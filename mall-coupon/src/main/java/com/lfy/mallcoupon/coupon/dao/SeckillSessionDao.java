package com.lfy.mallcoupon.coupon.dao;

import com.lfy.mallcoupon.coupon.entity.SeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author leifeiyang
 * @email 932190738@qq.com
 * @date 2023-07-05 09:55:23
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {
	
}
