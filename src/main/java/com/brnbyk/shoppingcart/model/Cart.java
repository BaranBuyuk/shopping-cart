package com.brnbyk.shoppingcart.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 04:19
 **/
public interface Cart {


    BigDecimal getTotalAmount();

    List<CartItem> getProducts();

    BigDecimal getTotalAmountAfterDiscounts();

    BigDecimal getCouponDiscount();

    BigDecimal getCampaignDiscount();

    BigDecimal getDeliveryCost();


}
