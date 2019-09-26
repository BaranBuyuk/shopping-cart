package com.brnbyk.shoppingcart.model;

import java.math.BigDecimal;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 02:50
 **/
public class CartItem {

    private Product product;
    private int quantity;


    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int size) {
        quantity += size;
    }

    public BigDecimal getTotalAmount() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;

        CartItem that = (CartItem) o;

        if (getQuantity() != that.getQuantity()) return false;
        return getProduct() != null ? getProduct().equals(that.getProduct()) : that.getProduct() == null;
    }

    @Override
    public int hashCode() {
        int result = getProduct() != null ? getProduct().hashCode() : 0;
        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}

