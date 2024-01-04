package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartDao
{
   ShoppingCart getByUserId(int userId);

    void addToCart(int userId, ShoppingCartItem cartItem, int productId);

    // Add a method to update the quantity of a product in the shopping cart
    void updateProductQuantity(int userId, int productId, int quantity);

    // Add a method to clear all products from the shopping cart
    void clearCart(int userId);
}
