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
import org.springframework.stereotype.Service;

import static com.example.demo.exception.CustomExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponValidator couponValidator;
    private final String INUSED_STATUS = "IN_USE";
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

        coupon.updateStatus(INUSED_STATUS);
        couponRepository.save(coupon);

        String key = "COUPON_USER_" + useCouponRequest.getUserId();
        redisUtil.setObjectExpire(key, useCouponRequest.getCouponId(), TIMER);      // 2분으로 설정

//        try{
//
//
//        }catch(Exception e){
//            throw new BadRequestException(UNAVAILABLE_COUPON);
//        }
    }
}
