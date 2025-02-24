package com.example.demo.coupon.controller;

import com.example.demo.coupon.dto.request.UseCouponRequest;
import com.example.demo.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @Operation(summary = "Use coupon")
    @PostMapping("/coupon")
    public ResponseEntity<?> useCoupon(@RequestBody final UseCouponRequest useCouponRequest) {
        couponService.useCoupon(useCouponRequest);
        return ResponseEntity.noContent().build();
    }
}
