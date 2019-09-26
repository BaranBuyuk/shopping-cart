package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.Coupon;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 01:12
 **/
public class CouponTest {


    @Test
    public void createValidCouponTest() {
        Coupon coupon = new Coupon(BigDecimal.valueOf(1), BigDecimal.valueOf(10), DiscountType.Rate);
        assertNotNull(coupon);
        assertEquals(BigDecimal.valueOf(1), coupon.getMinCouponAmount());
        assertEquals(BigDecimal.valueOf(10), coupon.getDiscountAmount());
        assertEquals(DiscountType.Rate, coupon.getDiscountType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCreateInvalidCouponTest() {
        new Coupon(null, BigDecimal.valueOf(10), DiscountType.Rate);
    }
}
