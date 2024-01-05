package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class ShoppingCartItem
{
    private Product product;
    private int quantity = 1;
    private BigDecimal discountPercent = BigDecimal.ZERO;


    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    @JsonIgnore
    public int getProductId()
    {
        // Check if product is not null before invoking its methods
        if (this.product != null) {
            return this.product.getProductId();
        } else {
            throw new IllegalStateException("Product is null. Cannot retrieve productId.");
        }
    }

    public BigDecimal getLineTotal()
    {
        BigDecimal basePrice = product.getPrice();
        BigDecimal quantity = new BigDecimal(this.quantity);

        BigDecimal subTotal = basePrice.multiply(quantity);
        BigDecimal discountAmount = subTotal.multiply(discountPercent);

        return subTotal.subtract(discountAmount);
    }
}
