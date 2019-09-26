package com.brnbyk.shoppingcart.discount.impl;

import com.brnbyk.shoppingcart.discount.DiscountStrategy;
import com.brnbyk.shoppingcart.discount.rule.impl.CampaignDiscountRule;
import com.brnbyk.shoppingcart.model.Campaign;
import com.brnbyk.shoppingcart.model.CartItem;
import com.brnbyk.shoppingcart.model.DiscountHolder;
import com.brnbyk.shoppingcart.model.ShoppingCart;
import com.brnbyk.shoppingcart.model.enums.DiscountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.brnbyk.shoppingcart.constants.Constants.ONE_HUNDRED;
import static com.brnbyk.shoppingcart.constants.Constants.ZERO;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 16:50
 **/
public class CampaignDiscountStrategy implements DiscountStrategy {

    private CampaignDiscountRule campaignDiscountRule;
    private List<Campaign> campaigns;

    public CampaignDiscountStrategy(ShoppingCart shoppingCart, List<Campaign> campaigns) {
        this.campaigns = campaigns;
        campaignDiscountRule = new CampaignDiscountRule(shoppingCart);
    }

    @Override
    public void calculateDiscount(ShoppingCart shoppingCart) {
        BigDecimal applicableMaxAmount = BigDecimal.valueOf(0.00);
        for (Campaign campaign : campaigns) {
            boolean applicableRule = campaignDiscountRule.isApplicableDiscount(campaign);
            if (applicableRule) {
                DiscountType discountType = campaign.getDiscountType();
                if (discountType.equals(DiscountType.Amount)) {
                    if (campaign.getAmount().compareTo(applicableMaxAmount) > ZERO) {
                        applicableMaxAmount = campaign.getAmount();
                    }
                } else if (discountType.equals(DiscountType.Rate)) {
                    applicableMaxAmount = calculatePercentageAmount(shoppingCart, applicableMaxAmount, campaign);
                } else {
                    throw new IllegalStateException(String.format("There is no implementation for Discount Type => %s", discountType.name()));
                }
            }
        }
        shoppingCart.getDiscountHolderMap().get(DiscountHolder.DiscountMethod.CAMPAIGN).setDiscountedAmount(applicableMaxAmount);
    }

    private BigDecimal calculatePercentageAmount(ShoppingCart shoppingCart, BigDecimal applicableMaxAmount, Campaign campaign) {
        Map<String, BigDecimal> totalAmountByCategory = getTotalAmountByCategory(shoppingCart);
        BigDecimal categoryTotalAmount = totalAmountByCategory.get(campaign.getCategory().getTitle());
        BigDecimal calculatedPercentage = categoryTotalAmount
                .multiply(campaign.getAmount())
                .divide(ONE_HUNDRED);
        if (calculatedPercentage.compareTo(applicableMaxAmount) > ZERO) {
            applicableMaxAmount = calculatedPercentage;
        }
        return applicableMaxAmount;
    }

    private Map<String, BigDecimal> getTotalAmountByCategory(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getCategory().getTitle(),
                        Collectors.reducing(BigDecimal.ZERO, CartItem::getTotalAmount, BigDecimal::add)));
    }
}

