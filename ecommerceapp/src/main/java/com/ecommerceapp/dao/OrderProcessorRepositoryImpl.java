package com.ecommerceapp.dao;

import com.ecommerceapp.model.Customer;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.exception.CustomerNotFoundException;
import com.ecommerceapp.exception.ProductNotFoundException;
import com.ecommerceapp.model.CartItem;
import com.ecommerceapp.model.OrderItem;
import com.ecommerceapp.util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The implementation class for the OrderProcessorRepository interface.
 * This class provides methods to interact with the database for managing
 * customers, products, carts, and orders in an e-commerce application.
 */

public  class OrderProcessorRepositoryImpl implements OrderProcessorRepository {
    private Connection connection;

    public OrderProcessorRepositoryImpl() {
        this.connection = DBConnUtil.getConnection();
        PreparedStatement ps;
        
    }
    /**
     * Creates a new product in the database with the provided details.
     *
     * @param product The Product object containing the details of the product to be created.
     * @return true if the product is created successfully, false otherwise.
     */
    @Override
    public boolean createProduct(Product product) {
        String query = "INSERT INTO products (product_id,name, price, description, stockquantity) VALUES (?, ?, ?, ?, ?)";
        try {	
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getPrice());
            ps.setString(4, product.getDescription());
            ps.setInt(5, product.getStockQuantity());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Creates a new customer in the database with the provided details.
     *
     * @param customer The Customer object containing the details of the customer to be created.
     * @return true if the customer is created successfully, false otherwise.
     */
    @Override
    public boolean createCustomer(Customer customer) {
        String query = "INSERT INTO customers (customer_id,name, email, password) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Deletes a product from the database based on the provided product ID.
     *
     * @param productId The ID of the product to be deleted.
     * @return true if the product is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Deletes a customer from the database based on the provided customer ID.
     *
     * @param customerId The ID of the customer to be deleted.
     * @return true if the customer is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Adds a product to the cart for the specified customer.
     * 
     * @param customer The customer adding the product to the cart.
     * @param product  The product to add to the cart.
     * @param quantity The quantity of the product to add.
     * @return True if the product was added successfully, false otherwise.
     */
    @Override
    public boolean addToCart(Customer customer, Product product, int quantity) {
        String query = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, quantity);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Removes a product to the cart for the specified customer.
     * 
     * @param customer The customer whos product needs to be removes.
     * @param product  The product to remove from the cart.
     * @return True if the product was removed successfully, false otherwise.
     */

    @Override
    public boolean removeFromCart(Customer customer, Product product) {
        String query = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all product from the cart for the specified customer.
     * 
     * @param customer The customer whos cart needs to be retrieved for view.
     * @return the cart items.
     */
    @Override
    public List<Map<String, Object>> getAllFromCart(Customer customer) {
        List<Map<String, Object>> cartItems = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT p.name, p.price, c.quantity " +
                           "FROM cart c " +
                           "JOIN products p ON c.product_id = p.product_id " +
                           "WHERE c.customer_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> cartItem = new HashMap<>();
                cartItem.put("name", rs.getString("name"));
                cartItem.put("price", rs.getDouble("price"));
                cartItem.put("quantity", rs.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return cartItems;
    }
    /**
     * Retrieves all customers from the system.
     * 
     * @return the available customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            String query = "SELECT * FROM customers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Customer customer = new Customer(id, name, email);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


    /**
     * Retrieves all product from the cart for the specified customer.
     * 
     * @param customer The customer whos cart needs to be retrieved for view.
     * @return the cart items.
     */
    @Override
    public List<CartItem> getAllCartItems(Customer customer) {
        List<CartItem> cartItems = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT product_id, quantity FROM cart WHERE customer_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, customer.getCustomerId());
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                CartItem cartItem = new CartItem(productId, quantity);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cartItems;
    }
    /**
     * Retrieves all product information from the database.
     *
     * @param productId The ID of the product to retrieve.
     * @return The products available in the system.
     */
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                String description = resultSet.getString("description");
                int stockQuantity = resultSet.getInt("stockquantity");
                Product product = new Product(id, name, price, description, stockQuantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Retrieves product information from the database based on the specified product ID.
     * If the product with the given ID is found, its details are returned; otherwise, null is returned.
     *
     * @param productId The ID of the product to retrieve.
     * @return The Product object containing the details of the product with the specified ID,
     *         or null if no product is found with the given ID.
     * @throws SQLException If an SQL exception occurs while executing the database query.
     */
    public Product getProductById(int productId) throws ProductNotFoundException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("price"),
                        rs.getString("description"),
                        rs.getInt("stockquantity")
                );
            } else {
                throw new ProductNotFoundException("Product with ID " + productId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProductNotFoundException("Failed to retrieve product with ID " + productId);
        }
    }

    /**
     * Places an order for a customer with the specified cart items and shipping address.
     * This method inserts a new order record into the database and adds corresponding order items.
     *
     * @param customer        The Customer object representing the customer placing the order.
     * @param cartItems       The list of CartItem objects representing the items in the customer's cart.
     * @param shippingAddress The shipping address for the order.
     * @return True if the order is successfully placed; false otherwise.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
    @Override
    public boolean placeOrder(Customer customer, List<CartItem> cartItems, String shippingAddress) {
        if (customer == null || cartItems == null || cartItems.isEmpty() || shippingAddress == null || shippingAddress.isEmpty()) {
            return false;
        }

        String insertOrderQuery = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, NOW(), ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateStockQuery = "UPDATE products SET stockquantity = stockquantity - ? WHERE product_id = ?";
        PreparedStatement insertOrderStatement = null;
        PreparedStatement insertOrderItemStatement = null;
        PreparedStatement updateStockStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);
            insertOrderStatement = connection.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            insertOrderStatement.setInt(1, customer.getCustomerId());
            double totalAmount = 0;

            for (CartItem cartItem : cartItems) {
                Product product = getProductById(cartItem.getProductId());
                if (product == null) {
                    throw new SQLException("Product with ID " + cartItem.getProductId() + " not found");
                }
                double productPrice = Double.parseDouble(product.getPrice());
                totalAmount += productPrice * cartItem.getQuantity();
            }

            insertOrderStatement.setDouble(2, totalAmount);
            insertOrderStatement.setString(3, shippingAddress);
            int affectedRows = insertOrderStatement.executeUpdate();

            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }

            generatedKeys = insertOrderStatement.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return false;
            }

            // Update stock quantity for each product in the order
            updateStockStatement = connection.prepareStatement(updateStockQuery);
            for (CartItem cartItem : cartItems) {
                updateStockStatement.setInt(1, cartItem.getQuantity());
                updateStockStatement.setInt(2, cartItem.getProductId());
                updateStockStatement.executeUpdate();
            }

            // Insert order items
            insertOrderItemStatement = connection.prepareStatement(insertOrderItemQuery);
            for (CartItem cartItem : cartItems) {
                insertOrderItemStatement.setInt(1, orderId);
                insertOrderItemStatement.setInt(2, cartItem.getProductId());
                insertOrderItemStatement.setInt(3, cartItem.getQuantity());
                insertOrderItemStatement.addBatch();
            }
            insertOrderItemStatement.executeBatch();

            connection.commit();
            return true;
        } catch (SQLException | ProductNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (insertOrderStatement != null) {
                    insertOrderStatement.close();
                }
                if (insertOrderItemStatement != null) {
                    insertOrderItemStatement.close();
                }
                if (updateStockStatement != null) {
                    updateStockStatement.close();
                }
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }





    /**
     * Retrieves a list of order items associated with the specified customer ID.
     * This method fetches all order items from the database for the given customer.
     *
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return A list of OrderItem objects representing the order items for the customer.
     * @throws SQLException If an SQL exception occurs while interacting with the database.
     */
    @Override
    public List<OrderItem> getOrdersByCustomer(int customerId) throws CustomerNotFoundException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE customer_id = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (orderItems.isEmpty()) {
            throw new CustomerNotFoundException("No orders found for customer with ID: " + customerId);
        }

        return orderItems;
    }

}
