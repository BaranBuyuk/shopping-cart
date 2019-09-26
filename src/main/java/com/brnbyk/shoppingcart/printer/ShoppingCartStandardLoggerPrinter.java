package com.brnbyk.shoppingcart.printer;

import com.brnbyk.shoppingcart.model.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Username = baranbuyuk
 * Date = 26.09.2019 04:19
 **/
public class ShoppingCartStandardLoggerPrinter implements Printer {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCartStandardLoggerPrinter.class.getName());

    private BigDecimal totalAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal deliveryCost;
    private Map<String, List<CartItem>> categories;


    public ShoppingCartStandardLoggerPrinter(Map<String, List<CartItem>> categories, BigDecimal totalAmount, BigDecimal totalDiscountAmount, BigDecimal deliveryCost) {
        this.categories = categories;
        this.totalAmount = totalAmount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.deliveryCost = deliveryCost;
    }

    @Override
    public void print() {
        LOGGER.log(Level.INFO, this::toString);
    }

    @Override
    public String toString() {
        return "ShoppingCartPrinter{" +
                "totalAmount=" + totalAmount +
                ", totalDiscountAmount=" + totalDiscountAmount +
                ", deliveryCost=" + deliveryCost +
                ", categories=" + categories +
                '}';
    }
}
