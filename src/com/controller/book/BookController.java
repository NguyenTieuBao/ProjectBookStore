/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller.book;

import com.JDBC.JDBC;
import com.model.book.BookModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tieu Bao
 */
public class BookController {
    
    public void insert(BookModel bm) {
        try {
            
            String query = "INSERT INTO book (id, isbn, title, publisher, price, quantity, author, description, status, categoryId)"
                    + " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM category WHERE name = ?))";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            
            ps.setString(1, bm.getIsbn());
            ps.setString(2, bm.getTitle());
            ps.setString(3, bm.getPublisher());
            ps.setDouble(4, bm.getPrice());
            ps.setInt(5, bm.getQuantity());
            ps.setString(6, bm.getAuthor());
            ps.setString(7, bm.getDescription());
            ps.setString(8, bm.getStatus());
            ps.setString(9, bm.getCategoryId());
            
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<BookModel> loadData() {
        List<BookModel> bm = new ArrayList<>();
        
        try {
            String query = "SELECT book.*, category.name "
                    + "FROM book "
                    + "JOIN category ON book.categoryId = category.id";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                double price = rs.getDouble("price");
                Integer quantity = rs.getInt("quantity");
                String author = rs.getString("author");
                String dess = rs.getString("description");
                String status = rs.getString("status");
                String categoryId = rs.getString("name");
                
                BookModel bookModel = new BookModel(id, isbn, title, publisher, price, quantity, author, dess, status, categoryId);
                bm.add(bookModel);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bm;
    }
    
    public BookModel getIdTable(int id) {
        BookModel bm = null;
        
        try {
            String query = "SELECT book.*, category.name "
                    + "FROM book "
                    + "JOIN category ON book.categoryId = category.id WHERE book.id = " + id;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer ids = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String publisher = rs.getString("publisher");
                double price = rs.getDouble("price");
                Integer quantity = rs.getInt("quantity");
                String author = rs.getString("author");
                String dess = rs.getString("description");
                String status = rs.getString("status");
                String categoryId = rs.getString("name");
                
                bm = new BookModel(id, isbn, title, publisher, price, quantity, author, dess, status, categoryId);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bm;
    }
    
    public void update(BookModel bm) {
        try {
            String query = "UPDATE book SET isbn = ?,"
                    + " title = ?, publisher = ?, price = ?,"
                    + " quantity = ?, author = ?, description = ?,"
                    + " status = ?,"
                    + "categoryId = (SELECT id FROM category WHERE name = ?)"
                    + " WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, bm.getIsbn());
            ps.setString(2, bm.getTitle());
            ps.setString(3, bm.getPublisher());
            ps.setDouble(4, bm.getPrice());
            ps.setInt(5, bm.getQuantity());
            ps.setString(6, bm.getAuthor());
            ps.setString(7, bm.getDescription());
            ps.setString(8, bm.getStatus());
            ps.setString(9, bm.getCategoryId());
            ps.setInt(10, bm.getId());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean delete(int id) {
        boolean d = false;
        try {
            String query = "DELETE FROM book WHERE id = ? ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            d = true;
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}


