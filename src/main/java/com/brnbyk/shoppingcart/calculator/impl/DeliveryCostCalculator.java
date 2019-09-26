package com.brnbyk.shoppingcart.calculator.impl;

import com.brnbyk.shoppingcart.calculator.Calculator;
import com.brnbyk.shoppingcart.model.CartItem;
import com.brnbyk.shoppingcart.model.Category;
import com.brnbyk.shoppingcart.model.Product;
import com.brnbyk.shoppingcart.model.ShoppingCart;

import java.math.BigDecimal;

import static com.brnbyk.shoppingcart.constants.Constants.FIXED_COST;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 04:45
 **/
public class DeliveryCostCalculator implements Calculator<ShoppingCart, BigDecimal> {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;

    public DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct) {
        validate(costPerDelivery, costPerProduct);
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    private void validate(BigDecimal costPerDelivery, BigDecimal costPerProduct) {
        if (costPerDelivery == null || costPerProduct == null) {
            throw new IllegalArgumentException("Cost per delivery / Cost per product should not be null");
        } else if (BigDecimal.ZERO.compareTo(costPerDelivery) >= 0 || BigDecimal.ZERO.compareTo(costPerProduct) >= 0) {
            throw new IllegalArgumentException("Cost per delivery / Cost per product should be greater than zero");
        }
    }

    @Override
    public BigDecimal calculate(ShoppingCart shoppingCart) {
        long numberOfDeliveries = calculateNumberOfDeliveries(shoppingCart);
        long numberOfProducts = calculateNumberOfProducts(shoppingCart);
        BigDecimal multiplyDelivery = costPerDelivery.multiply(new BigDecimal(numberOfDeliveries));
        BigDecimal multiplyProduct = costPerProduct.multiply(new BigDecimal(numberOfProducts));
        return multiplyDelivery.add(multiplyProduct).add(FIXED_COST);
    }

    private long calculateNumberOfDeliveries(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getCategory)
                .map(Category::getTitle)
                .distinct().count();
    }

    private long calculateNumberOfProducts(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getTitle)
                .distinct().count();
    }
}
