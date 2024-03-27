-- J212 Jai Kishore R E-Commerce Case Study

create database ecom;
use ecom;
CREATE TABLE customers(
    customer_id  INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25),
    email VARCHAR(30),
    password varchar(25)
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25),
    price VARCHAR(20),
    description VARCHAR(60),
    stockquantity INT
);

CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    product_id INT,
    FOREIGN KEY (customer_id)
        REFERENCES customers (customer_id),
	 FOREIGN KEY (product_id)
        REFERENCES products (product_id),
	quantity int
);
    
CREATE TABLE orders (
    order_id INT PRIMARY KEY auto_increment,
    customer_id INT,
    FOREIGN KEY (customer_id)
        REFERENCES customers (customer_id) ,
    order_date DATE,
    total_price INT,
    shipping_address varchar(100)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY auto_increment,
    order_id INT,
    FOREIGN KEY (order_id)
        REFERENCES orders (order_id) ,
    product_id int,
    FOREIGN KEY (product_id)
        REFERENCES products (product_id) ,
    quantity INT	
);
select * from customers;
select * from products;
select * from cart;	
select * from orders;
select * from order_items;

drop table customers;
drop table products;
drop table cart;
drop table orders;
drop table order_items;
