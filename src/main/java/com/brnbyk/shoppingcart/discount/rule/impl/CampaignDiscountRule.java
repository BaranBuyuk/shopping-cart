package com.brnbyk.shoppingcart.discount.rule.impl;

import com.brnbyk.shoppingcart.discount.rule.DiscountRule;
import com.brnbyk.shoppingcart.model.Campaign;
import com.brnbyk.shoppingcart.model.CartItem;
import com.brnbyk.shoppingcart.model.ShoppingCart;

import java.util.List;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 17:56
 **/
public class CampaignDiscountRule implements DiscountRule<Campaign> {

    private ShoppingCart shoppingCart;

    public CampaignDiscountRule(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public boolean isApplicableDiscount(Campaign campaign) {
        if (campaign != null) {
            String categoryTitle = campaign.getCategory().getTitle();
            long totalQuantitySizeOfCategory = getTotalQuantitySizeOfCategory(categoryTitle, shoppingCart.getProducts());
            return totalQuantitySizeOfCategory > campaign.getMinimumProductQuantityLengthForApplicableDiscount();
        }
        return false;
    }

    private long getTotalQuantitySizeOfCategory(String categoryTitle, List<CartItem> products) {
        if (products != null && !products.isEmpty()) {
            return products.stream()
                    .filter(cartItem -> cartItem.getProduct().getCategory().getTitle().equals(categoryTitle))
                    .mapToInt(CartItem::getQuantity)
                    .sum();
        }
        return 0;
    }
}
