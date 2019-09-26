package com.brnbyk.shoppingcart.test.discount.rule;

import com.brnbyk.shoppingcart.discount.rule.DiscountRule;
import com.brnbyk.shoppingcart.discount.rule.impl.CouponDiscountRule;
import com.brnbyk.shoppingcart.model.*;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 03:00
 **/
public class CouponDiscountRuleTest {

    @Test
    public void applicableCouponDiscountRuleTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(50), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(25), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(19), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 2);
        shoppingCart.addItem(orange, 20);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Campaign campaign = new Campaign(electronic, BigDecimal.valueOf(1), 10, DiscountType.Rate);
        shoppingCart.applyCampaigns(campaign);

        Coupon coupon = new Coupon(BigDecimal.valueOf(1), BigDecimal.valueOf(10), DiscountType.Rate);

        DiscountRule discountRule = new CouponDiscountRule(shoppingCart);
        boolean applicableDiscount = discountRule.isApplicableDiscount(coupon);

        assertTrue(applicableDiscount);
    }

    @Test
    public void notApplicableCouponDiscountRuleTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(50), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(25), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(19), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 2);
        shoppingCart.addItem(orange, 20);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Campaign campaign = new Campaign(electronic, BigDecimal.valueOf(1), 10, DiscountType.Rate);
        shoppingCart.applyCampaigns(campaign);

        Coupon coupon = new Coupon(BigDecimal.valueOf(110000), BigDecimal.valueOf(10), DiscountType.Rate);

        DiscountRule discountRule = new CouponDiscountRule(shoppingCart);
        boolean applicableDiscount = discountRule.isApplicableDiscount(coupon);

        assertFalse(applicableDiscount);
    }
}
