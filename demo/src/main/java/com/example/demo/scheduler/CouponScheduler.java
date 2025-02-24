package com.example.demo.scheduler;

import com.example.demo.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponScheduler {

    private final CouponService couponService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void expireCouponScheduler() {
        int expiredCount = couponService.expireCoupons();
        log.info("[스케줄러 실행] 만료된 쿠폰 개수: {}", expiredCount);
    }
}
