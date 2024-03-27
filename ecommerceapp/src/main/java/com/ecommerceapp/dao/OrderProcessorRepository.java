package com.ecommerceapp.dao;

import java.util.List;
import java.util.Map;

import com.ecommerceapp.exception.CustomerNotFoundException;
import com.ecommerceapp.exception.OrderNotFoundException;
import com.ecommerceapp.model.CartItem;
import com.ecommerceapp.model.Customer;
import com.ecommerceapp.model.OrderItem;
import com.ecommerceapp.model.Product;

public interface OrderProcessorRepository {
    boolean createProduct(Product product);
    boolean createCustomer(Customer customer);
    boolean deleteProduct(int productId);
    boolean deleteCustomer(int customerId);
    boolean addToCart(Customer customer, Product product, int quantity);
    boolean removeFromCart(Customer customer, Product product);
    boolean placeOrder(Customer customer, List<CartItem> cartItems, String shippingAddress);
    List<OrderItem> getOrdersByCustomer(int customerId) throws CustomerNotFoundException, OrderNotFoundException;
    List<Map<String, Object>> getAllFromCart(Customer customer);
    List<CartItem> getAllCartItems(Customer customer);
	List<Product> getAllProducts();
	List<Customer> getAllCustomers();


}
