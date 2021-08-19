package springtest.service;

import springtest.model.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getCoupons();
    void deleteCoupon(Long id);
}
