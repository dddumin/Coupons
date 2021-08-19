package springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springtest.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
