package springtest.service;


import springtest.exceptions.IncorrectHashException;
import springtest.model.Category;
import springtest.model.Company;
import springtest.model.Coupon;

import java.util.List;

public interface CompanyService extends ClientService {
    void addCoupon(String hash, Coupon coupon) throws IncorrectHashException;
    void updateCoupon(String hash, Coupon coupon) throws IncorrectHashException;
    void deleteCoupon(String hash, Long couponId) throws IncorrectHashException;
    List<Coupon> getCompanyCoupons(String hash, Category category) throws IncorrectHashException;
    List<Coupon> getCompanyCoupons(String hash, double maxPrice) throws IncorrectHashException;
    List<Coupon> getCompanyCoupons(String hash) throws IncorrectHashException;
    Coupon getCouponById(String hash, Long id) throws IncorrectHashException;
    Company getCompanyDetails(String hash) throws IncorrectHashException;
}
