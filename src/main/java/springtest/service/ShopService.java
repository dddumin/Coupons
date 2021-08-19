package springtest.service;

import springtest.exceptions.IncorrectHashException;
import springtest.model.Company;
import springtest.model.Coupon;

import java.util.List;

public interface ShopService {
    List<Company> getAllCompanies(String hash) throws IncorrectHashException;
    List<Coupon> getCoupons(String hash) throws IncorrectHashException;
    Coupon getCoupon(String hash, Long id) throws IncorrectHashException;
    Company getCompany(String hash, Long id) throws IncorrectHashException;

}
