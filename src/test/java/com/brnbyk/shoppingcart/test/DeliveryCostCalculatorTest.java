package com.brnbyk.shoppingcart.test;

import com.brnbyk.shoppingcart.calculator.impl.DeliveryCostCalculator;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import com.brnbyk.shoppingcart.model.ShoppingCart;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 05:04
 **/
public class DeliveryCostCalculatorTest {

    private static final BigDecimal COST_PER_DELIVERY = new BigDecimal("10.00");
    private static final BigDecimal COST_PER_PRODUCT = new BigDecimal("10.00");

    @Test
    public void deliveryCostCalculatorTest() {
        Category electronicCategory = Category.createCategory("Electronics");
        Category bookCategory = Category.createCategory("Book");
        Product apple = new Product("Apple Iphone X", new BigDecimal(2000), electronicCategory);
        Product book = new Product("The God Delusion", new BigDecimal("120"), bookCategory);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(apple, 4);
        shoppingCart.addItem(book, 5);
        shoppingCart.addItem(apple, 2);
        shoppingCart.addItem(book, 2);

        DeliveryCostCalculator deliveryCalculator = new DeliveryCostCalculator(COST_PER_DELIVERY, COST_PER_PRODUCT);
        BigDecimal expectedDeliveryCost = new BigDecimal("42.99");
        assertEquals(expectedDeliveryCost, deliveryCalculator.calculate(shoppingCart));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCostPerDeliveryOrCostPerProductIsNull() {
        ShoppingCart shoppingCart = new ShoppingCart();
        DeliveryCostCalculator deliveryCalculator = new DeliveryCostCalculator(BigDecimal.valueOf(1), null);
        deliveryCalculator.calculate(shoppingCart);
    }
}
