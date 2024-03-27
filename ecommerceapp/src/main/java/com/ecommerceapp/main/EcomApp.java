package com.ecommerceapp.main;

import com.ecommerceapp.dao.OrderProcessorRepository;
import com.ecommerceapp.dao.OrderProcessorRepositoryImpl;
import com.ecommerceapp.exception.CustomerNotFoundException;
import com.ecommerceapp.exception.OrderNotFoundException;
import com.ecommerceapp.model.Customer;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.CartItem;
import com.ecommerceapp.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Main class for the Ecommerce Application.
 * This class contains the main method and provides a user interface
 * for interacting with the e-commerce system.
 * @author jaikishore
 */
public class EcomApp {
    private static OrderProcessorRepository opr = new OrderProcessorRepositoryImpl();
    private static Scanner in = new Scanner(System.in);
    /**
     * Main method to start the Ecommerce Application.
     * @param args Command-line arguments (not used).
     * @throws CustomerNotFoundException if no customer with id specified
     * @throws OrderNotFoundException if order with id specified is not found
     */
    public static void main(String[] args) throws CustomerNotFoundException, OrderNotFoundException {
        displayMenu();
    }
    /**
     * Displays the main menu of the application.
     */
    private static void displayMenu() throws CustomerNotFoundException, OrderNotFoundException {
        System.out.println("Welcome to Ecommerce Application");
        System.out.println("Menu");
        System.out.println("Enter (1/2/3/4/5/6/7/8/9/10)");
        System.out.println("1. Register Customer");
        System.out.println("2. View All Products");
        System.out.println("3. Create Product");
        System.out.println("4. Delete Product");
        System.out.println("5. View All Customers");
        System.out.println("6. Add to Cart");
        System.out.println("7. View Cart");
        System.out.println("8. Place Order");
        System.out.println("9. View Customer Order");
        System.out.println("10. Exit");
       
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1:
            	
                registerCustomer();
                break;
            case 2:
                viewAllProducts(); 
                break;
            case 3:
                createProduct();
                break;
            case 4:
            	
                deleteProduct();
                break;
            case 5:
                viewAllCustomers(); 
                break;
            case 6:
            	
                addToCart();
                break;
            case 7:

                viewCart();
                break;
            case 8:
            	
                placeOrder();
                break;
            case 9:
                viewCustomerOrder();
                break;
            case 10:
                System.out.println("Ecommerce Application is closed");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }

        displayMenu();
    }
    /**
     * Registers a new customer.
     */
    private static void registerCustomer() {

        System.out.println("Enter customer name:");
        String name = in.nextLine().trim();

        System.out.println("Enter customer email:");
        String email = in.nextLine().trim();

        System.out.println("Enter customer password:");
        String password = in.nextLine().trim();

        Customer customer = new Customer(name, email, password);
        boolean success = opr.createCustomer(customer);
        if (success) {
            System.out.println("Customer registered successfully");
        } else {
            System.out.println("Failed to register customer");
        }
    }
    /**
     * View all products.
     */
    private static void viewAllProducts() {
        List<Product> products = opr.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Available Products:");
            for (Product product : products) {
                System.out.println("Product ID: " + product.getProductId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Description: " + product.getDescription() + ", Stock Quantity: " + product.getStockQuantity());
            }
        }
    }
    /**
     * Creates a new product.
     */
    private static void createProduct() {
    	
        System.out.println("Enter product name:");
        String name = in.nextLine().trim();
        System.out.println("Enter product price:");
        String price = in.nextLine().trim();
        System.out.println("Enter product description:");
        String description = in.nextLine().trim();
        System.out.println("Enter product stock quantity:");
        int stockQuantity = in.nextInt();
        in.nextLine();
        Product product = new Product( name, price, description, stockQuantity);
        boolean success = opr.createProduct(product);
        if (success) {
            System.out.println("Product created successfully");
        } else {
            System.out.println("Failed to create product");
        }
    }
    /**
     * Deletes a product.
     */
    private static void deleteProduct() {
        System.out.println("Enter product id to delete:");
        int productId = in.nextInt();
        boolean success = opr.deleteProduct(productId);
        if (success) {
            System.out.println("Product deleted successfully");
        } else {
            System.out.println("Failed to delete product");
        }
    }
    /**
     * Adds a product to the cart.
     */
    private static void addToCart() {
        System.out.println("Enter customer id:");
        int customerId = in.nextInt();
        in.nextLine(); 
        System.out.println("Enter product id:");
        int productId = in.nextInt();
        in.nextLine(); 
        System.out.println("Enter quantity:");
        int quantity = in.nextInt();
        in.nextLine(); 

        Customer customer = new Customer(customerId);
        Product product = new Product(productId);
        boolean success = opr.addToCart(customer, product, quantity);
        if (success) {
            System.out.println("Product added to cart successfully");
        } else {
            System.out.println("Failed to add product to cart");
        }
    }
    /**
     * Displays All Registered Customers.
     */
    private static void viewAllCustomers() {
        List<Customer> customers = opr.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            System.out.println("Available Customers:");
            for (Customer customer : customers) {
                System.out.println("Customer ID: " + customer.getCustomerId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail());
            }
        }
    }

    /**
     * Displays the contents of the cart.
     */
    private static void viewCart() {
        System.out.println("Enter customer id:");
        int customerId = in.nextInt();
        in.nextLine(); 

        Customer customer = new Customer(customerId);
        List<Map<String, Object>> cartItems = opr.getAllFromCart(customer);
        System.out.println("Products in cart:");
        for (Map<String, Object> cartItem : cartItems) {
            String name = (String) cartItem.get("name");
            double price = (double) cartItem.get("price");
            int quantity = (int) cartItem.get("quantity");
            System.out.println(name + " - Rs." + price + " - Quantity: " + quantity);
        }
    }

    /**
     * Places an order for the items in the cart.
     */
    private static void placeOrder() throws CustomerNotFoundException, OrderNotFoundException {
        System.out.println("Enter customer id:");
        int customerId = in.nextInt();
        in.nextLine(); 
        System.out.println("Enter shipping address:");
        String shippingAddress = in.nextLine();

        Customer customer = new Customer(customerId);
        List<CartItem> cartItems = opr.getAllCartItems(customer);

        if (!cartItems.isEmpty()) {
            boolean success = opr.placeOrder(customer, cartItems, shippingAddress);
            if (success) {
                System.out.println("Order placed successfully");
                List<OrderItem> orderItems = opr.getOrdersByCustomer(customerId);
                if (!orderItems.isEmpty()) {
                    System.out.println("Order items for customer id: " + customerId);
                    for (OrderItem orderItem : orderItems) {
                        System.out.println("Order ID: " + orderItem.getOrderId() + ", Product ID: " + orderItem.getProductId() + ", Quantity: " + orderItem.getQuantity());
                    }
                } else {
                    System.out.println("No orders found for customer id: " + customerId);
                }
            } else {
                System.out.println("Failed to place order");
            }
        } else {
            System.out.println("No cart items found for customer id: " + customerId);
        }
    }


    /**
     * Views the orders made by a specific customer.
     */

    private static void viewCustomerOrder() throws CustomerNotFoundException, OrderNotFoundException {
        System.out.println("Enter customer id:");
        int customerId = in.nextInt();
        in.nextLine(); 

        List<OrderItem> orderItems = opr.getOrdersByCustomer(customerId);
        if (orderItems.isEmpty()) {
            System.out.println("No orders found for customer id: " + customerId);
        } else {
            System.out.println("Orders for customer id: " + customerId);
            for (OrderItem orderItem : orderItems) {
                System.out.println("Order ID: " + orderItem.getOrderId() + ", Product ID: " + orderItem.getProductId() + ", Quantity: " + orderItem.getQuantity());
            }
        }
    }
}
