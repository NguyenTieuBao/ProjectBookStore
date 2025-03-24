/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JDBC;

import java.sql.*;
/**
 *
 * @author Tieu Bao
 */
public class JDBC {

    public static Connection getConnection() {
	String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/project_book_store";
	String user = "root";
	String password = "";
		
	try {
            try {
		Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
		e.printStackTrace();
            }
		return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
                e.printStackTrace();
        }
		
            return null;
    }
    
    public void closeConnection() {
        try {
            Connection connection = JDBC.getConnection();
            
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
