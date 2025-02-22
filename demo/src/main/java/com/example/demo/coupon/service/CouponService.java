package com.example.demo.coupon.service;

import com.example.demo.coupon.domain.Coupon;
import com.example.demo.coupon.dto.request.CouponValidationequest;
import com.example.demo.coupon.dto.request.UseCouponRequest;
import com.example.demo.coupon.repository.CouponRepository;
import com.example.demo.coupon.util.RedisUtil;
import com.example.demo.coupon.validator.CouponValidator;
import com.example.demo.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.demo.exception.CustomExceptionStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponValidator couponValidator;
    private final String AVAILABLE_STATUS = "AVAILABLE";
    private final String INUSE_STATUS = "IN_USE";
    private final String APPLIED_STATUS = "APPLIED";
    private final String EXPIRED_STATUS = "EXPIRED";
    private final RedisUtil redisUtil;
    private final int TIMER = 120;

    @Transactional
    public void useCoupon(final UseCouponRequest useCouponRequest) {
        String status = couponRepository.findStatusById(useCouponRequest.getCouponId());

        CouponValidationequest couponValidationequest =
                CouponValidationequest.of(useCouponRequest,
                        status);

        couponValidator.validateCoupon(couponValidationequest);     // 쿠폰 검증

        Coupon coupon =
                couponRepository
                        .findById(couponValidationequest.getCouponId())
                        .orElseThrow(() -> new BadRequestException(NOT_EXISITED_COUPON_ID));

        log.info("[쿠폰 상태 변경 시도] 쿠폰 ID: {}, 현재 상태: {}", couponValidationequest.getCouponId(), coupon.getStatus());
        coupon.updateStatus(INUSE_STATUS);
        couponRepository.save(coupon);
        log.info("[쿠폰 상태 변경 완료] 쿠폰 ID: {}, 현재 상태: {}", couponValidationequest.getCouponId(), coupon.getStatus());

        String key = "USE_COUPON_" + couponValidationequest.getCouponId();
        redisUtil.setObjectExpire(key, couponValidationequest.getUserId(), TIMER);      // 2분으로 설정
        log.info("[쿠폰 REDIS 저장] key: {}, value: {}, TTL: {}초", key, couponValidationequest.getUserId(), TIMER);
    }

    @Transactional
    public void updateCouponStatusToExpired(Long couponId){
        Coupon coupon = couponRepository.findById(couponId)
                        .orElseThrow(() -> new BadRequestException(NOT_EXISITED_COUPON_ID));

        log.info("[쿠폰 상태 변경 시도] 쿠폰 ID: {}, 현재 상태: {}", couponId, coupon.getStatus());
        coupon.updateStatus(APPLIED_STATUS);
        couponRepository.save(coupon);
        log.info("[쿠폰 상태 변경 완료] 쿠폰 ID: {}, 현재 상태: {}", couponId, coupon.getStatus());
    }
}
