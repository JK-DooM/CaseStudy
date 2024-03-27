package com.ecommerceapp.model;
/**
 * Represents Cart Items in the ecommerce system.
 */
public class CartItem {
    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;
    private Product product;


    public CartItem() {
    }

    public CartItem(int cartId, int customerId, int productId, int quantity) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public CartItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}