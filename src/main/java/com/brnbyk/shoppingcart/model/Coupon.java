package com.brnbyk.shoppingcart.model;

import com.brnbyk.shoppingcart.discount.Discountable;
import com.brnbyk.shoppingcart.model.enums.DiscountType;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.ZERO;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 04:13
 **/
public class Coupon implements Discountable {

    private BigDecimal minCouponAmount;
    private BigDecimal discountAmount;
    private DiscountType discountType;

    public Coupon(BigDecimal minCouponAmount, BigDecimal discountAmount, DiscountType discountType) {
        validateParameters(minCouponAmount, discountAmount);
        this.minCouponAmount = minCouponAmount;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    private void validateParameters(BigDecimal minCouponAmount, BigDecimal discountAmount) {
        if (minCouponAmount == null || BigDecimal.ZERO.compareTo(minCouponAmount) >= ZERO) {
            throw new IllegalArgumentException("Min Coupon Amount must be greater than zero.");
        } else if (discountAmount == null || BigDecimal.ZERO.compareTo(discountAmount) >= ZERO) {
            throw new IllegalArgumentException("Discount Amount must be greater than zero.");
        }
    }

    public BigDecimal getMinCouponAmount() {
        return minCouponAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public DiscountType getDiscountType() {
        return discountType;
    }


}
