package com.brnbyk.shoppingcart.test.model;

import com.brnbyk.shoppingcart.model.Campaign;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.enums.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 01:30
 **/
public class CampaignTest {

    @Test
    public void createValidCampaignTest() {
        Category category = Category.createCategory("Electornics");
        Campaign campaign = new Campaign(category, BigDecimal.TEN, 10, DiscountType.Rate);

        assertNotNull(campaign.getCategory());
        assertEquals(campaign.getCategory().getTitle(), campaign.getCategory().getTitle());
        assertEquals(BigDecimal.TEN, campaign.getAmount());
        assertEquals(10, campaign.getMinimumProductQuantityLengthForApplicableDiscount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCategoryIsNullTest() {
        new Campaign(null, BigDecimal.TEN, 10, DiscountType.Rate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCampaignAmountIsNullOrLessThanZeroTest() {
        Category category = Category.createCategory("Electornics");
        Campaign campaign = new Campaign(category, BigDecimal.valueOf(-1), 10, DiscountType.Rate);
    }
}
