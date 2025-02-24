package com.example.demo.scheduler;

import com.example.demo.coupon.service.CouponService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CouponSchedulerTest {
    @MockBean  // 실제 Bean 대신 Mock을 사용하여 실행 여부만 체크
    private CouponService couponService;

    @Autowired
    private CouponScheduler couponScheduler;

    @Test
    public void 스케줄러가_CouponService를_정상적으로_호출하는지_테스트() {
        // when (스케줄러 실행)
        couponScheduler.expireCouponScheduler();

        // then (CouponService.expireCoupons()가 한 번 호출되었는지 확인)
        verify(couponService, times(1)).expireCoupons();
    }
}
