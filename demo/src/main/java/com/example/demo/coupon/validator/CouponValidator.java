package com.example.demo.coupon.validator;

import com.example.demo.coupon.dto.request.CouponValidationequest;
import com.example.demo.coupon.repository.CouponRepository;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import static com.example.demo.exception.CustomExceptionStatus.*;

@Component
@RequiredArgsConstructor
public class CouponValidator {
    private final CouponRepository couponRepository;
    private final String availableStatus = "AVAILABLE";
    private final String inuseStatus = "IN_USE";
    private final String appliedStatus = "APPLIED";
    private final String expiredStatus = "EXPIRED";

    public void validateCoupon(final CouponValidationequest couponValidationequest){
        
        // 쿠폰 존재 여부 확인
        if(!isExisited(couponValidationequest.getCouponId())){ 
            throw new BadRequestException(NOT_EXISITED_COUPON_ID);
        }
        // 쿠폰 발급자-쿠폰 사용자 동일인 확인 
        if(!isMatched(couponValidationequest.getCouponId(), couponValidationequest.getUserId())){ 
            throw new BadRequestException(NOT_MATCHED_USER);
        }
        // 쿠폰 사용 가능 상태 확인
        validateCouponStatus(couponValidationequest.getStatus());
    }

    public boolean isExisited(long couponId){
        return couponRepository.existsById(couponId);
    }

    public boolean isMatched(long couponId, long userId){
        long idBycoupon = couponRepository.findUserIdById(couponId);
        if(idBycoupon != userId) return false;
        else return true;
    }

    public void validateCouponStatus(String status){
        if(status.equals(availableStatus)){
            return;
        }else if(status.equals(inuseStatus)){
            throw new BadRequestException(IN_USE_COUPON_STATUS);
        }else if(status.equals(appliedStatus)){
            throw new BadRequestException(APPLIED_COUPON_STATUS);
        }else if(status.equals(expiredStatus)){
            throw new BadRequestException(EXPIRED_COUPON_STATUS);
        }else{
            throw new BadRequestException(INVALID_COUPON_STATUS);
        }
    }
}
