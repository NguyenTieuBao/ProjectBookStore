
package com.controller.billDetail;

import com.JDBC.JDBC;

import com.model.billDetail.BillDetailModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDetailController {
    
    public void insert(BillDetailModel bdm) {
        try {
            String query = "INSERT INTO bill_detail (id, book_id, bill_id, price, quantity, discount) "
                    + "VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            
            ps.setInt(1, bdm.getBookId());
            ps.setInt(2, bdm.getBillId());
            ps.setDouble(3, bdm.getPrice());
            ps.setDouble(4, bdm.getQuantity());
            ps.setDouble(5, bdm.getDiscount());
            ps.executeUpdate();
            
            // Trừ số lượng quantity trong bảng "book"
            String updateBookQuery = "UPDATE book SET quantity = quantity - ? WHERE id = ?";
            PreparedStatement updateBookPs = JDBC.getConnection().prepareStatement(updateBookQuery);
            updateBookPs.setDouble(1, bdm.getQuantity());
            updateBookPs.setInt(2, bdm.getBookId());
            updateBookPs.executeUpdate();

            JDBC.getConnection().close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public List<BillDetailModel> loadDataBillDetail() {
        List<BillDetailModel> list = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM bill_detail";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("id");
                Integer bookId = rs.getInt("book_id");
                Integer billId = rs.getInt("bill_id");
                Double price = rs.getDouble("price");
                Double quantity = rs.getDouble("quantity");
                Double discount = rs.getDouble("discount");
                BillDetailModel bdm = new BillDetailModel(id, bookId, billId, price, quantity, discount);
                list.add(bdm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int readIdBillDetail() {
        int id = 0;
        try {
            String query = "SELECT id FROM bill_detail";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public void deleteById(int id, int bookId, double quantity) {
        try {
            // Cập nhật lại quantity trong bảng book
            String updateBookQuery = "UPDATE book SET quantity = quantity + ? WHERE id = ?";
            try (PreparedStatement updateBookPs = JDBC.getConnection().prepareStatement(updateBookQuery)) {
                updateBookPs.setDouble(1, quantity);
                updateBookPs.setInt(2, bookId);
                updateBookPs.executeUpdate();
            }
            String query = "DELETE FROM bill_detail WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public int getLatestBillDetailId() {
        int latestBillDetailId = 0;
        
        try {
            String query = "SELECT MAX(id) FROM bill_detail";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                latestBillDetailId = rs.getInt(1);
            }
            JDBC.getConnection().close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return latestBillDetailId;
    }  
}
