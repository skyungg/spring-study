package com.example.demo.coupon.util;

import com.example.demo.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisExpirationListener implements MessageListener {

    private final RedisUtil redisUtil;
    private final CouponService couponService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString(); // 만료된 Key 값 가져오기
        log.info("Redis Key 만료 감지: {}", expiredKey);

        try{
            String[] parts = expiredKey.split("_");
            Long couponId = Long.parseLong(parts[2]);       // 만료된 key에서 쿠폰 ID 가져오기 (USE_COUPON_쿠폰번호)
            log.info("만료된 쿠폰 ID: {}", couponId);

            couponService.updateCouponStatusToExpired(couponId);

        }catch(Exception e){
            log.error("Redis TTL 만료 처리 중 오류 발생 : ", e.getMessage(), e);
        }
    }
}
