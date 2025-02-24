package com.example.demo.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Builder
@Validated
@AllArgsConstructor
public class UseCouponRequest {

    @Schema(description = "쿠폰 사용을 원하는 사용자 아이디")
    @NotNull
    private Long userId;

    @Schema(description = "현재 사용하려는 쿠폰 아이디")
    @NotNull
    private Long couponId;
}
