package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.CartItem;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 01:37
 **/
public class CartItemTest {


    @Test
    public void createValidCartItemTest() {
        Category category = Category.createCategory("Electronic");
        Product product = new Product("Apple IPhone", BigDecimal.valueOf(100), category);
        CartItem cartItem = new CartItem(product, 1);
        assertNotNull(cartItem);
        assertEquals("Apple IPhone", cartItem.getProduct().getTitle());
        assertEquals("Electronic", cartItem.getProduct().getCategory().getTitle());
        assertEquals(1, cartItem.getQuantity());
    }

    @Test
    public void getTotalAmountWithQuantityTest() {
        Category category = Category.createCategory("Electronic");
        Product product = new Product("Apple IPhone", BigDecimal.valueOf(28), category);
        CartItem cartItem = new CartItem(product, 8);

        assertNotNull(cartItem);
        assertEquals(BigDecimal.valueOf(224), cartItem.getTotalAmount());
    }

    @Test
    public void increaseQuantityAndGetTotalAmountWithNewQuantityTest() {
        Category category = Category.createCategory("Electronic");
        Product product = new Product("Apple IPhone", BigDecimal.valueOf(28), category);
        CartItem cartItem = new CartItem(product, 8);

        assertNotNull(cartItem);
        assertEquals(BigDecimal.valueOf(224), cartItem.getTotalAmount());

        cartItem.increaseQuantity(2);

        assertEquals(BigDecimal.valueOf(280), cartItem.getTotalAmount());
    }
}
