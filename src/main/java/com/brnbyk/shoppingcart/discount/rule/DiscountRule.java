package com.brnbyk.shoppingcart.discount.rule;

import com.brnbyk.shoppingcart.discount.Discountable;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 17:59
 **/
public interface DiscountRule<T extends Discountable> {

    boolean isApplicableDiscount(T t);

}
