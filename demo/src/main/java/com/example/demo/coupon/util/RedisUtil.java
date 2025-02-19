package com.example.demo.coupon.util;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate redisTemplate;

    public void setObjectExpire(String key, Object object, int duration) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration expiredDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, object, expiredDuration);
    }
}
