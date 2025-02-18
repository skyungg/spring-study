package com.example.demo.exception;

import com.example.demo.coupon.dto.response.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomExceptionStatus implements BaseErrorCode {
    INTERNAL_SERVER_ERROR("InternalServer_500_1", "Server error"),
    INVALID_REQUEST("BadRequest_400_1", "Invalid request"),
    REQUEST_ERROR("NotValidInput_400_2", "Invalid input"),
    NOT_EXISITED_COUPON_ID("CouponValidationError_400_1", "존재하지 않는 쿠폰 아이디입니다."),
    NOT_MATCHED_USER("CouponValidationError_400_2", "쿠폰 발급자와 사용자가 일치하지 않습니다."),
    IN_USE_COUPON_STATUS("CouponValidationError_400_3", "사용 중인 쿠폰입니다."),
    APPLIED_COUPON_STATUS("CouponValidationError_400_4", "이미 사용한 쿠폰입니다."),
    EXPIRED_COUPON_STATUS("CouponValidationError_400_5", "만료된 쿠폰입니다."),
    INVALID_COUPON_STATUS("CouponValidationError_400_6", "유효하지 않은 쿠폰 상태입니다."),
    UNAVAILABLE_COUPON("CouponValidationError_400_7", "사용 불가능한 쿠폰입니다.");

    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).build();
    }
}
