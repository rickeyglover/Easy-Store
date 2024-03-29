package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlProductDao dao;

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT sc.*, p.name, p.price, p.category_id, p.description, p.color, p.stock, p.featured, p.image_url " +
                "FROM shopping_cart sc " +
                "JOIN products p ON sc.product_id = p.product_id " +
                "WHERE sc.user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = new ShoppingCart();

            while (resultSet.next()) {
                ShoppingCartItem shoppingCartItem = mapRow(resultSet);

                // Add the item to the ShoppingCart using the product ID as the key
                shoppingCart.add(shoppingCartItem);
            }

            return shoppingCart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToCart(int userId, ShoppingCartItem cartItem, int productId) {

//        Product product = new Product();


        try (Connection connection = getConnection()) {
            // Check if the product is already in the cart
            if (isProductInCart(userId, productId)){
                // If the product is in the cart, update the quantity
                updateProductQuantity(userId, productId, cartItem.getQuantity());
            } else {
                // If the product is not in the cart, add it
                String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, userId);
                    statement.setInt(2, productId);
                    statement.setInt(3, cartItem.getQuantity());
                    statement.executeUpdate();

//                    Product product = dao.getById()

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProductQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isProductInCart(int userId, int productId) throws SQLException {
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    protected static ShoppingCartItem mapRow(ResultSet row) throws SQLException {
        int productId = row.getInt("product_id");
        String name = row.getString("name");
        BigDecimal price = row.getBigDecimal("price");
        int categoryId = row.getInt("category_id");
        String description = row.getString("description");
        String color = row.getString("color");
        int stock = row.getInt("stock");
        boolean isFeatured = row.getBoolean("featured");
        String imageUrl = row.getString("image_url");

        int quantity = row.getInt("quantity");  // Assuming you have a "quantity" column in the result set

        Product product = new Product(productId, name, price, categoryId, description, color, stock, isFeatured, imageUrl);
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        return item;
    }
}
