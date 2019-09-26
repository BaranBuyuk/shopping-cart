package com.brnbyk.shoppingcart.model;

import com.brnbyk.shoppingcart.calculator.impl.DeliveryCostCalculator;
import com.brnbyk.shoppingcart.discount.impl.CampaignDiscountStrategy;
import com.brnbyk.shoppingcart.discount.impl.CouponDiscountStrategy;
import com.brnbyk.shoppingcart.discount.impl.DiscountStrategyContext;
import com.brnbyk.shoppingcart.printer.ShoppingCartStandardLoggerPrinter;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.brnbyk.shoppingcart.constants.Constants.COST_PER_DELIVERY;
import static com.brnbyk.shoppingcart.constants.Constants.COST_PER_PRODUCT;

/**
 * Username = baranbuyuk
 * Date = 25.09.2019 02:39
 **/
public class ShoppingCart implements Cart {


    private List<CartItem> products = new ArrayList<>();
    private Map<DiscountHolder.DiscountMethod, DiscountHolder> discountHolderMap = new EnumMap<>(DiscountHolder.DiscountMethod.class);
    private boolean isCampaignDiscountAppliedBefore;
    private DeliveryCostCalculator deliveryCostCalculator;


    public ShoppingCart() {
        discountHolderMap.put(DiscountHolder.DiscountMethod.CAMPAIGN, new DiscountHolder(BigDecimal.ZERO));
        discountHolderMap.put(DiscountHolder.DiscountMethod.COUPON, new DiscountHolder(BigDecimal.ZERO));
    }


    public void addItem(Product product, int quantity) {
        validate(product, quantity);
        addToProducts(product, quantity);

    }

    private void validate(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("You can't apply null product to shopping cart");
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity of product should greater than zero");
        }
    }

    private void addToProducts(Product product, int quantity) {
        boolean isExistSameProduct = products.stream().anyMatch(p -> p.getProduct().getTitle().equals(product.getTitle()));
        if (!isExistSameProduct) {
            CartItem cartItem = new CartItem(product, quantity);
            products.add(cartItem);
        } else {
            Optional<CartItem> optionalProduct = products.stream().filter(p -> p.getProduct().getTitle().equals(product.getTitle())).findFirst();
            optionalProduct.ifPresent(cartItem -> cartItem.increaseQuantity(quantity));
        }
    }

    @Override
    public List<CartItem> getProducts() {
        return products;
    }

    @Override
    public BigDecimal getTotalAmountAfterDiscounts() {
        return getTotalAmount().subtract(getCouponDiscount().add(getCampaignDiscount()));
    }

    @Override
    public BigDecimal getCouponDiscount() {
        return discountHolderMap.get(DiscountHolder.DiscountMethod.COUPON).getDiscountedAmount();
    }

    @Override
    public BigDecimal getCampaignDiscount() {
        return discountHolderMap.get(DiscountHolder.DiscountMethod.CAMPAIGN).getDiscountedAmount();
    }

    @Override
    public BigDecimal getDeliveryCost() {
        if (deliveryCostCalculator == null) {
            deliveryCostCalculator = new DeliveryCostCalculator(COST_PER_DELIVERY, COST_PER_PRODUCT);
        }
        return deliveryCostCalculator.calculate(this);
    }

    public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return products.stream().map(CartItem::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyCampaigns(Campaign... campaigns) {
        DiscountStrategyContext discountStrategyContext = new DiscountStrategyContext(new CampaignDiscountStrategy(this, Arrays.asList(campaigns)));
        discountStrategyContext.applyDiscount(this);
        isCampaignDiscountAppliedBefore = true;
    }

    public void applyCoupon(Coupon coupon) {
        DiscountStrategyContext discountStrategyContext = new DiscountStrategyContext(new CouponDiscountStrategy(this, coupon));
        discountStrategyContext.applyDiscount(this);
    }

    public void print() {

        Map<String, List<CartItem>> groupedByCategories = getProducts()
                .stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getCategory().getTitle()));

        ShoppingCartStandardLoggerPrinter shoppingCartStandardLoggerPrinter = new ShoppingCartStandardLoggerPrinter(groupedByCategories,
                getTotalAmount(), getCampaignDiscount().add(getCouponDiscount()), getDeliveryCost());

        shoppingCartStandardLoggerPrinter.print();

    }

    public Map<DiscountHolder.DiscountMethod, DiscountHolder> getDiscountHolderMap() {
        return discountHolderMap;
    }

    public boolean isCampaignDiscountAppliedBefore() {
        return isCampaignDiscountAppliedBefore;
    }
}


