package springtest.service;

import springtest.exceptions.IncorrectHashException;
import springtest.model.Category;
import springtest.model.Coupon;
import springtest.model.Customer;

import java.util.List;

public interface CustomerService extends ClientService{
    void purchaseCoupon(String hash, Coupon coupon) throws IncorrectHashException;
    List<Coupon> getCustomerCoupons(String hash, Category category) throws IncorrectHashException;
    List<Coupon> getCustomerCoupons(String hash) throws IncorrectHashException;
    List<Coupon> getCustomerCoupons(String hash, double maxPrice) throws IncorrectHashException;
    Customer getCustomerDetails(String hash) throws IncorrectHashException;
}
