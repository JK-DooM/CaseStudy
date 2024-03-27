package com.ecommerceapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
static Connection con;
	
	public static Connection getConnection() {
		try {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecom","root","LuffyZoroSanji1!");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args) {
		System.out.println(getConnection());
	}

}
