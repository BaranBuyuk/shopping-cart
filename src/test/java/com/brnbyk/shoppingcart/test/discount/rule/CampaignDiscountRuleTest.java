package com.brnbyk.shoppingcart.test.discount.rule;

import com.brnbyk.shoppingcart.discount.rule.DiscountRule;
import com.brnbyk.shoppingcart.discount.rule.impl.CampaignDiscountRule;
import com.brnbyk.shoppingcart.model.Campaign;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import com.brnbyk.shoppingcart.model.ShoppingCart;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 03:07
 **/
public class CampaignDiscountRuleTest {

    @Test
    public void applicableCampaignDiscountRuleTest() {

        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 2);
        shoppingCart.addItem(orange, 20);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        DiscountRule discountRule = new CampaignDiscountRule(shoppingCart);
        Campaign campaign = new Campaign(electronic, BigDecimal.valueOf(1), 1, DiscountType.Rate);
        boolean applicableDiscount = discountRule.isApplicableDiscount(campaign);
        assertTrue(applicableDiscount);

    }

    @Test
    public void notApplicableCampaignDiscountRuleTest() {

        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 2);
        shoppingCart.addItem(orange, 20);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        DiscountRule discountRule = new CampaignDiscountRule(shoppingCart);
        Campaign campaign = new Campaign(electronic, BigDecimal.valueOf(1), 3, DiscountType.Rate);
        boolean applicableDiscount = discountRule.isApplicableDiscount(campaign);
        assertFalse(applicableDiscount);

    }
}
