package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {

            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Use the shoppingCartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }

    @PostMapping("/products/add/{productId}")
    public void addProductToCart(Principal principal, ShoppingCartItem item,@PathVariable int productId) {
        try {
            if (principal == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
            }

            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();



            // You may want to validate the existence of the product before adding it to the cart
            int quantity = 1; // Set the initial quantity (you can modify this based on your needs)

            shoppingCartDao.addToCart(userId, item, productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }

    @PutMapping("/products/{productId}")
    public void updateProductInCart(Principal principal, @PathVariable int productId, @RequestBody ShoppingCartItem item) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();


            if(shoppingCartDao.isProductInCart(userId, productId)) {
            // Use the shoppingCartDao to update the quantity of the specified product in the cart
            shoppingCartDao.updateProductQuantity(userId, productId, item.getQuantity());}
            else{
                //System.out.println("Sorry, item is not already currently in cart to be updated");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry, item is not already currently in cart to be updated");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }

    @DeleteMapping
    public void clearCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // Use the shoppingCartDao to clear the entire cart for the current user
            shoppingCartDao.clearCart(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.", e);
        }
    }
}