package springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.model.Coupon;
import springtest.repository.CouponRepository;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService{
    private CouponRepository couponRepository;

    @Autowired
    public void setCouponRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> getCoupons() {
        return this.couponRepository.findAll();
    }

    @Override
    public void deleteCoupon(Long id) {
        this.couponRepository.deleteById(id);
    }
}
