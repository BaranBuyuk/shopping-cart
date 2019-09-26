package com.brnbyk.shoppingcart.test.discount;

import com.brnbyk.shoppingcart.discount.impl.CampaignDiscountStrategy;
import com.brnbyk.shoppingcart.discount.impl.DiscountStrategyContext;
import com.brnbyk.shoppingcart.model.Campaign;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import com.brnbyk.shoppingcart.model.ShoppingCart;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 03:17
 **/
public class DiscountStrategyContextTest {

    @Test
    public void shouldFindMaxAmountForTwoCampaignTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 5);
        shoppingCart.addItem(orange, 10);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 1, DiscountType.Amount);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(100), 1, DiscountType.Rate);

        DiscountStrategyContext discountStrategyContext = new
                DiscountStrategyContext(new CampaignDiscountStrategy(shoppingCart, Arrays.asList(electronicCampaign, foodCampaign)));
        discountStrategyContext.applyDiscount(shoppingCart);

        assertEquals(BigDecimal.valueOf(1000), shoppingCart.getCampaignDiscount());


    }

    @Test
    public void shouldNotApplicableGivenCampaignsTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 5);
        shoppingCart.addItem(orange, 10);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 6, DiscountType.Amount);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(100), 11, DiscountType.Rate);

        DiscountStrategyContext discountStrategyContext = new
                DiscountStrategyContext(new CampaignDiscountStrategy(shoppingCart, Arrays.asList(electronicCampaign, foodCampaign)));
        discountStrategyContext.applyDiscount(shoppingCart);

        assertEquals(BigDecimal.valueOf(0.0), shoppingCart.getCampaignDiscount());

    }
}
