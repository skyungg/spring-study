package com.example.demo.exception;

import com.example.demo.coupon.dto.response.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();
}
