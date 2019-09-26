package com.brnbyk.shoppingcart.model;

import com.brnbyk.shoppingcart.discount.Discountable;
import com.brnbyk.shoppingcart.model.enums.DiscountType;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.ZERO;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 03:00
 **/
public class Campaign implements Discountable {

    private Category category;
    private BigDecimal amount;
    private long minimumProductSizeForApplicableDiscount;
    private DiscountType discountType;

    public Campaign(Category category, BigDecimal amount, int minimumProductSizeForApplicableDiscount, DiscountType discountType) {
        validate(category, amount, minimumProductSizeForApplicableDiscount);
        this.category = category;
        this.amount = amount;
        this.minimumProductSizeForApplicableDiscount = minimumProductSizeForApplicableDiscount;
        this.discountType = discountType;
    }

    private void validate(Category category, BigDecimal amount, int minimumProductSizeForApplicableDiscount) {
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        } else if (category.getTitle() == null || category.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Category title must not be null or empty");
        } else if (amount == null || BigDecimal.ZERO.compareTo(amount) >= ZERO) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else if (minimumProductSizeForApplicableDiscount < 0) {
            throw new IllegalArgumentException("minimumProductSizeForApplicableDiscount must be greater than zero");
        }
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getMinimumProductQuantityLengthForApplicableDiscount() {
        return minimumProductSizeForApplicableDiscount;
    }

    @Override
    public DiscountType getDiscountType() {
        return discountType;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "category=" + category +
                ", amount=" + amount +
                ", minimumProductSizeForApplicableDiscount=" + minimumProductSizeForApplicableDiscount +
                ", discountType=" + discountType +
                '}';
    }
}
