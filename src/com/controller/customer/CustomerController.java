/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller.customer;

import com.JDBC.JDBC;
import com.model.customer.CustomerModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class CustomerController {
    
    public void insert(CustomerModel cm) {
        try {
            String query = "INSERT INTO customer VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, cm.getName());
            ps.setObject(2, cm.getDate());
            ps.setString(3, cm.getPhone());
            ps.setString(5, cm.getAddress());
            ps.setString(4, cm.getGender());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<CustomerModel> loadData() {
        List<CustomerModel> list = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM customer";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {   
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                CustomerModel cm = new CustomerModel(id, name, dob, phone, address, gender);
                list.add(cm);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public CustomerModel getIdTable(int id) {
        CustomerModel cm = null;
        
        try {
            String query = "SELECT * FROM customer WHERE id = " + id;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer ids = rs.getInt("id");
                String name = rs.getString("name");
                Date date = rs.getDate("dob");
                String phone = rs.getString("phone");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                
                cm = new CustomerModel(id, name, date, phone, address, gender);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cm;
    }
    
    public void update(CustomerModel cm) {
        try {
            String query = "UPDATE customer SET name = ?,"
                    + " dob = ?, phone = ?, "
                    + "gender = ?, address = ?"
                    + "WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, cm.getName());
            ps.setObject(2, cm.getDate());
            ps.setString(3, cm.getPhone());
            ps.setString(4, cm.getGender());
            ps.setString(5, cm.getAddress());
            ps.setInt(6, cm.getId());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        try {
            String query = "DELETE FROM customer WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
