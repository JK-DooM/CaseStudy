package com.ecommerceapp.model;
/**
 * Represents a customer in the ecommerce system.
 */
public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String password;

    

    public Customer(int customerId, String name, String email, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }


	public Customer() {
	}



	public Customer(String name, String email, String password) {
		this.name = name;
        this.email = email;
        this.password = password;
	}

    public Customer(int customerId, String name, String email) {
    	this.customerId = customerId;
        this.name = name;
        this.email = email;
	}

	public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}