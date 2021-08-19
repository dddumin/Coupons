package springtest.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springtest.model.Coupon;
import springtest.service.CouponService;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleTasks {
    private CouponService couponService;

    @Autowired
    public void setAdminService(CouponService couponService) {
        this.couponService = couponService;
    }

    @Scheduled(fixedRate = 1000*60*60)
    public void scheduleDeleteCoupons() {
        List<Coupon> coupons = this.couponService.getCoupons();
        for (Coupon coupon : coupons) {
            if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
                this.couponService.deleteCoupon(coupon.getId());
            }
        }
    }
}
