package com.brnbyk.shoppingcart.model;

import java.math.BigDecimal;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 22:40
 **/
public class DiscountHolder {

    private BigDecimal discountedAmount = BigDecimal.ZERO;

    public DiscountHolder(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public BigDecimal getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public enum DiscountMethod {
        CAMPAIGN, COUPON
    }
}
