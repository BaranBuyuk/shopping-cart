package com.brnbyk.shoppingcart.model;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.ZERO;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 01:11
 **/

public class Product {

    private String title;
    private BigDecimal price;
    private Category category;

    public Product(String title, BigDecimal price, Category category) {
        validateParameters(title, price, category);
        this.title = title;
        this.price = price;
        this.category = category;
    }

    private void validateParameters(String title, BigDecimal price, Category category) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty");
        } else if (price == null || BigDecimal.ZERO.compareTo(price) >= ZERO) {
            throw new IllegalArgumentException("Price must not be less than 0");
        } else if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
