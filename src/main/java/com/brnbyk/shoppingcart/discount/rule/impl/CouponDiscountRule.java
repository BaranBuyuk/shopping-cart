package com.brnbyk.shoppingcart.discount.rule.impl;

import com.brnbyk.shoppingcart.discount.rule.DiscountRule;
import com.brnbyk.shoppingcart.model.Coupon;
import com.brnbyk.shoppingcart.model.ShoppingCart;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.ZERO;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 18:23
 **/
public class CouponDiscountRule implements DiscountRule<Coupon> {

    private ShoppingCart shoppingCart;

    public CouponDiscountRule(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean isApplicableDiscount(Coupon coupon) {
        if (shoppingCart.isCampaignDiscountAppliedBefore()) {
            BigDecimal minCouponAmount = coupon.getMinCouponAmount();
            BigDecimal totalAmountOfShoppingCart = shoppingCart.getTotalAmount().subtract(shoppingCart.getCampaignDiscount());
            return totalAmountOfShoppingCart.compareTo(minCouponAmount) > ZERO;
        } else {
            throw new IllegalStateException("Please apply campaign discount before apply coupon discount");
        }
    }
}
