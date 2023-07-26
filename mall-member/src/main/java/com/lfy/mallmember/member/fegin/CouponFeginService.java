package com.lfy.mallmember.member.fegin;/*
 *@Author:user
 *@Date:7/5/2023 3:57 PM
 */

import com.lfy.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-coupon")
public interface CouponFeginService {

    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();
}
