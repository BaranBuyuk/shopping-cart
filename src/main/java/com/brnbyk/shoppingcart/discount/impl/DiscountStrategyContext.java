package com.brnbyk.shoppingcart.discount.impl;

import com.brnbyk.shoppingcart.discount.DiscountStrategy;
import com.brnbyk.shoppingcart.model.ShoppingCart;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 18:32
 **/
public class DiscountStrategyContext {

    private DiscountStrategy discountStrategy;

    public DiscountStrategyContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void applyDiscount(ShoppingCart shoppingCart) {
        discountStrategy.calculateDiscount(shoppingCart);
    }


}
