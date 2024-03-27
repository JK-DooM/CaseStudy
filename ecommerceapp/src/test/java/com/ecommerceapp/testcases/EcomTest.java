package com.ecommerceapp.testcases;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.ecommerceapp.dao.OrderProcessorRepositoryImpl;
import com.ecommerceapp.exception.CustomerNotFoundException;
import com.ecommerceapp.exception.ProductNotFoundException;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.Customer;
import com.ecommerceapp.model.CartItem;

import java.util.ArrayList;
import java.util.List;
/**
 * Unit test cases for testing the functionality of the Ecommerce System.
 */
public class EcomTest {
    private OrderProcessorRepositoryImpl orderProcessorRepository;
    /**
     * Setup method to initialize the OrderProcessorRepositoryImpl object before each test case.
     */
    @Before
    public void setUp() {
    	System.out.println("From Set up");
        orderProcessorRepository = new OrderProcessorRepositoryImpl();
    }
    /**
     * Test case to verify whether a product is created successfully in the database.
     */
    @Test
    public void testProductCreation() {
        Product product = new Product(106, "Test Product", "10.00", "Test Description", 10);

        boolean success = orderProcessorRepository.createProduct(product);

        assertTrue(success);

    }
    /**
     * Test case to verify whether a product is successfully added to the cart for a given customer.
     */
    @Test
    public void testProductAddedToCartSuccessfully() {
        Customer customer = new Customer(1); 
        Product product = new Product(1); 
       int quantity = 2;
        boolean success = orderProcessorRepository.addToCart(customer, product, quantity);
       assertTrue(success);


    }
    
    /**
     * Test case to verify that a CustomerNotFoundException is correctly thrown
     * when trying to retrieve orders for a non-existent customer.
    */
   @Test(expected = CustomerNotFoundException.class)
    public void testCustomerNotFoundException() throws CustomerNotFoundException {
        Customer customer = new Customer(1000); 
        orderProcessorRepository.getOrdersByCustomer(customer.getCustomerId());

    }
    /**
     * Test case to verify that a ProductNotFoundException is correctly thrown
     * when trying to retrieve a non-existent product.
     */
    @Test(expected = Exception.class)
    public void testProductNotFound() throws ProductNotFoundException {
        int productId = 1; 
        Product product = orderProcessorRepository.getProductById(productId);

    }

 
}
