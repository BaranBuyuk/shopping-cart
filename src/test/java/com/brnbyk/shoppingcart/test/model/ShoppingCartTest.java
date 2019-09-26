package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.*;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 03:21
 **/
public class ShoppingCartTest {

    private final String CATEGORY_NAME = "Electronics";
    private final String PRODUCT_TITLE = "Apple Iphone X";

    @Test
    public void createShoppingCartTest() {
        Category category = Category.createCategory(CATEGORY_NAME);
        Product product = new Product(PRODUCT_TITLE, BigDecimal.valueOf(12000), category);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(product, 2);

        assertEquals("Electronics", product.getCategory().getTitle());
        assertEquals("Apple Iphone X", product.getTitle());
        assertFalse(shoppingCart.getProducts().isEmpty());
        assertEquals(1, shoppingCart.getProducts().size());
        assertEquals(new BigDecimal(24000), shoppingCart.getTotalAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTryToAddNullProductToShoppingCartTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQuantityIsZeroOrLessThanZeroTest() {
        Category category = Category.createCategory(CATEGORY_NAME);
        Product product = new Product(PRODUCT_TITLE, BigDecimal.valueOf(12000), category);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(product, 0);
    }

    @Test
    public void getGroupedCategoriesOfTotalAmountsTest() {

        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(50), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(25), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(19), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 9);
        shoppingCart.addItem(orange, 1);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Map<String, BigDecimal> groupedTotalAmount = shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getCategory().getTitle(),
                        Collectors.reducing(BigDecimal.ZERO, CartItem::getTotalAmount, BigDecimal::add)));

        assertEquals(4, groupedTotalAmount.keySet().size());
        assertEquals(4, groupedTotalAmount.values().size());

        assertEquals(new BigDecimal(900), groupedTotalAmount.get("Electronics"));
        assertEquals(new BigDecimal(50), groupedTotalAmount.get("Food"));
        assertEquals(new BigDecimal(75), groupedTotalAmount.get("Book"));
        assertEquals(new BigDecimal(38), groupedTotalAmount.get("Life"));
    }

    @Test
    public void applyOneCampaignToShoppingCartTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(50), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(20), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(20), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 10);
        shoppingCart.addItem(orange, 1);
        shoppingCart.addItem(noMan, 3);
        shoppingCart.addItem(cycle, 2);

        Campaign campaign = new Campaign(electronic, BigDecimal.valueOf(100), 8, DiscountType.Amount);
        shoppingCart.applyDiscounts(campaign);

        BigDecimal discountedAmount = shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.CAMPAIGN).getDiscountedAmount();
        assertNotNull(discountedAmount);
        assertEquals(BigDecimal.valueOf(100), shoppingCart.getCampaignDiscount());
        assertEquals(BigDecimal.valueOf(100), discountedAmount);
        assertEquals(BigDecimal.valueOf(1050), shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test
    public void applyTwoCampaignAndShouldBeApplyRateDiscountTypeToShoppingCartTest() {
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

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 1, DiscountType.Amount);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(25), 2, DiscountType.Rate);
        shoppingCart.applyDiscounts(electronicCampaign, foodCampaign);

        BigDecimal discountedAmount = shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.CAMPAIGN).getDiscountedAmount();
        assertNotNull(discountedAmount);
        assertEquals(BigDecimal.valueOf(250), shoppingCart.getCampaignDiscount());
        assertEquals(BigDecimal.valueOf(250), discountedAmount);
        assertEquals(BigDecimal.valueOf(1063), shoppingCart.getTotalAmountAfterDiscounts());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenTryToNoImplementationDiscountTypeTest() {
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

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 1, DiscountType.NoImplementation);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(25), 2, DiscountType.Rate);
        shoppingCart.applyDiscounts(electronicCampaign, foodCampaign);
    }

    @Test
    public void applyValidCouponWithAmountDiscountTypeTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 1);
        shoppingCart.addItem(orange, 1);
        shoppingCart.addItem(noMan, 1);
        shoppingCart.addItem(cycle, 1);

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 1, DiscountType.Amount);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(25), 2, DiscountType.Rate);
        shoppingCart.applyDiscounts(electronicCampaign, foodCampaign);

        Coupon coupon = new Coupon(BigDecimal.valueOf(200), BigDecimal.valueOf(100), DiscountType.Amount);
        shoppingCart.applyCoupon(coupon);

        assertEquals(BigDecimal.valueOf(100), shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.COUPON).getDiscountedAmount());
        assertEquals(BigDecimal.valueOf(100), shoppingCart.getCouponDiscount());
        assertEquals(new BigDecimal("300.0"), shoppingCart.getTotalAmountAfterDiscounts());
    }


    @Test
    public void applyValidCouponWithRateDiscountTypeTest() {
        Category electronic = Category.createCategory("Electronics");
        Category food = Category.createCategory("Food");
        Category book = Category.createCategory("Book");
        Category life = Category.createCategory("Life");

        Product apple = new Product("Iphone", BigDecimal.valueOf(100), electronic);
        Product orange = new Product("Orange", BigDecimal.valueOf(100), food);
        Product noMan = new Product("NoMan", BigDecimal.valueOf(100), book);
        Product cycle = new Product("Cycle", BigDecimal.valueOf(100), life);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 1);
        shoppingCart.addItem(orange, 1);
        shoppingCart.addItem(noMan, 1);
        shoppingCart.addItem(cycle, 1);

        Campaign electronicCampaign = new Campaign(electronic, BigDecimal.valueOf(100), 1, DiscountType.Amount);
        Campaign foodCampaign = new Campaign(food, BigDecimal.valueOf(25), 2, DiscountType.Rate);
        shoppingCart.applyDiscounts(electronicCampaign, foodCampaign);

        Coupon coupon = new Coupon(BigDecimal.valueOf(200), BigDecimal.valueOf(10), DiscountType.Rate);
        shoppingCart.applyCoupon(coupon);

        assertEquals(BigDecimal.valueOf(40.0), shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.COUPON).getDiscountedAmount());
        assertEquals(BigDecimal.valueOf(40.0), shoppingCart.getCouponDiscount());

    }


    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenTryToApplyCouponDiscountBeforeCampaignDiscountTest() {
        ShoppingCart shoppingCart = new ShoppingCart();

        Coupon coupon = new Coupon(BigDecimal.valueOf(200), BigDecimal.valueOf(100), DiscountType.Amount);
        shoppingCart.applyCoupon(coupon);
    }

}