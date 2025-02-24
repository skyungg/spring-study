package com.example.demo.coupon.repository;

import com.example.demo.coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT c.status FROM Coupon c WHERE c.id = :couponId")
    String findStatusById(@Param("couponId") long id);

    boolean existsById(@Param("couponId") long id);

    @Query("SELECT c.user.id FROM Coupon c WHERE c.id = :couponId")
    long findUserIdById(@Param("couponId") long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
            "UPDATE Coupon c SET c.status = 'EXPIRED' WHERE c.expiredAt <= :currentDateTime AND c.status = 'AVAILABLE'")
    int expireCoupons(@Param("currentDateTime") LocalDateTime currentDateTime);
}
