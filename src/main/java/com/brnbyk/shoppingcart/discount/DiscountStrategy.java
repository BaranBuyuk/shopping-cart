package com.brnbyk.shoppingcart.discount;

import com.brnbyk.shoppingcart.model.ShoppingCart;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 02:46
 **/
public interface DiscountStrategy {

    void calculateDiscount(ShoppingCart shoppingCart);

}
