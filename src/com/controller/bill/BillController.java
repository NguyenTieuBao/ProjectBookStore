/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller.bill;

import com.JDBC.JDBC;
import com.itextpdf.text.pdf.codec.BmpImage;
import com.model.bill.BillModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tieu Bao
 */
public class BillController {
    
    public void insert(BillModel bm) {
        try {
            String query = "INSERT INTO bill (id, customerId, employeeId, _time, _date, total) "
                    + "VALUES (?, ?, ?, ?, ?, 0)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, bm.getId());
            ps.setString(2, bm.getCustomerId());
            ps.setString(3, bm.getEmployeeId());
            ps.setObject(4, bm.getTime());
            ps.setObject(5, bm.getDate());
            
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean searchId(int id) {
        boolean b = false;
        try {
            String query = "SELECT id FROM bill WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {                
               b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    public void delete(int id) {
        try {
            String query = "DELETE FROM bill WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<BillModel> loadData() {
        List<BillModel> billList = new ArrayList<>();

        try {
            String query = "SELECT bill.*, customer.name AS customer_name, employee.name AS employee_name "
                + "FROM bill "
                + "JOIN customer ON bill.customerId = customer.id "
                + "JOIN employee ON bill.employeeId = employee.id "
                + "ORDER BY bill.id DESC";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                LocalTime time = rs.getTime("_time").toLocalTime();
                java.sql.Date sqlDate = rs.getDate("_date");
                LocalDate date = sqlDate.toLocalDate();

                String employeeName = rs.getString("employee_name");
                double total = rs.getDouble("total");

                BillModel bill = new BillModel(id, customerName, time, date, employeeName, total);
                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }
    
    public List<BillModel> loadDataDate(LocalDate date) {
        List<BillModel> billList = new ArrayList<>();

        try {
            String query = "SELECT bill.*, customer.name AS customer_name, employee.name AS employee_name "
                + "FROM bill "
                + "JOIN customer ON bill.customerId = customer.id "
                + "JOIN employee ON bill.employeeId = employee.id "
                + "WHERE bill._date = ? "
                + "ORDER BY bill.id DESC";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            ps.setDate(1, sqlDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                LocalTime time = rs.getTime("_time").toLocalTime();
                java.sql.Date retrievedSqlDate = rs.getDate("_date");
                LocalDate retrievedDate = retrievedSqlDate.toLocalDate();

                String employeeName = rs.getString("employee_name");
                double total = rs.getDouble("total");

                BillModel bill = new BillModel(id, customerName, time, retrievedDate, employeeName, total);
                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }
    
    public double calculateTotalPrice(int billId) {
        double totalPrice = 0;
        try {
            String query = "SELECT SUM(price) FROM bill_detail WHERE bill_id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalPrice = rs.getDouble(1);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPrice;
    }
    
    public void updateTotal(double total, int id) {
        try {
            String query = "UPDATE bill SET total = ? WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setDouble(1, total);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getLatestBillId() {
        int latestBillId = 0;
        
        try {
            String query = "SELECT MAX(id) FROM bill";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                latestBillId = rs.getInt(1);
            }
            JDBC.getConnection().close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return latestBillId;
    }
}
