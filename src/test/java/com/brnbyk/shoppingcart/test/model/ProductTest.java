package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 02:07
 **/
public class ProductTest {

    private final String CATEGORY = "Electronic";
    private final String PRODUCT_TITLE = "Apple Iphone X";

    @Test
    public void createSuccessProductTest() {
        Category electronic = Category.createCategory(CATEGORY);
        Product product = new Product(PRODUCT_TITLE, BigDecimal.valueOf(800), electronic);

        assertNotNull(electronic);
        assertNotNull(product);

        assertEquals(PRODUCT_TITLE, product.getTitle());
        assertEquals(new BigDecimal(800), product.getPrice());
        assertEquals(CATEGORY, product.getCategory().getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenProductTitleIsNullOrEmptyTest() {
        Category electronic = Category.createCategory(CATEGORY);
        new Product("", BigDecimal.valueOf(800), electronic);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenProductPriceLessThanZeroTest() {
        Category electronic = Category.createCategory(CATEGORY);
        new Product(PRODUCT_TITLE, BigDecimal.valueOf(0), electronic);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenProductCategoryIsNullTest() {
        new Product(PRODUCT_TITLE, BigDecimal.valueOf(0), null);

    }
}
