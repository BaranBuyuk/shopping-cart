package com.brnbyk.shoppingcart.discount.impl;

import com.brnbyk.shoppingcart.discount.DiscountStrategy;
import com.brnbyk.shoppingcart.discount.rule.impl.CouponDiscountRule;
import com.brnbyk.shoppingcart.model.Coupon;
import com.brnbyk.shoppingcart.model.DiscountHolder;
import com.brnbyk.shoppingcart.model.ShoppingCart;
import com.brnbyk.shoppingcart.model.enums.DiscountType;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.ONE_HUNDRED;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 18:48
 **/
public class CouponDiscountStrategy implements DiscountStrategy {

    private CouponDiscountRule couponRuleEngine;
    private Coupon coupon;

    public CouponDiscountStrategy(ShoppingCart shoppingCart, Coupon coupon) {
        this.coupon = coupon;
        couponRuleEngine = new CouponDiscountRule(shoppingCart);
    }

    @Override
    public void calculateDiscount(ShoppingCart shoppingCart) {
        BigDecimal appliedCouponAmount = BigDecimal.valueOf(0.00);
        boolean isApplicableCoupon = couponRuleEngine.isApplicableDiscount(coupon);
        if (isApplicableCoupon) {
            if (coupon.getDiscountType().equals(DiscountType.Amount)) {
                appliedCouponAmount = coupon.getDiscountAmount();
            } else if (coupon.getDiscountType().equals(DiscountType.Rate)) {
                appliedCouponAmount = shoppingCart.getTotalAmount()
                        .subtract(shoppingCart.getCampaignDiscount())
                        .multiply(coupon.getDiscountAmount())
                        .divide(ONE_HUNDRED);
            }
        }
        shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.COUPON).setDiscountedAmount(appliedCouponAmount);
    }
}
