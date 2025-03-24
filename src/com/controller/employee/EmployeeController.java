
package com.controller.employee;

import com.JDBC.JDBC;
import com.model.employee.EmployeeModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EmployeeController {
    
    public void insert(EmployeeModel em) {
        try {
            String query = "INSERT INTO employee VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, em.getName());
            ps.setObject(2, em.getDob());
            ps.setString(3, em.getPhone());
            ps.setString(4, em.getMail());
            ps.setString(5, em.getGender());
            ps.setString(6, em.getStatus());
            ps.setString(7, em.getAddress());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<EmployeeModel> loadData() {
        List<EmployeeModel> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM employee";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String mail = rs.getString("mail");
                String gender = rs.getString("gender");
                String status = rs.getString("status");
                String address = rs.getString("address");
                EmployeeModel em = new EmployeeModel(id, name, dob, phone, mail, gender, status, address);
                list.add(em);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public EmployeeModel getTableId(int id) {
        EmployeeModel em = null;
        
        try {
            String query = "SELECT * FROM employee WHERE id = " + id;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Integer ids = rs.getInt("id");
                String name = rs.getString("name");
                Date dob = rs.getDate("dob");
                String phone = rs.getString("phone");
                String mail = rs.getString("mail");
                String gender = rs.getString("gender");
                String status = rs.getString("status");
                String address = rs.getString("address");
                em = new EmployeeModel(ids, name, dob, phone, mail, gender, status, address);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return em;
    }
    
    public void update(EmployeeModel em) {
        try {
            String query = "UPDATE employee "
                    + "SET name = ?, dob = ?,"
                    + " phone = ?, mail = ?,"
                    + " gender = ?, status = ?,"
                    + " address = ? WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, em.getName());
            ps.setObject(2, em.getDob());
            ps.setString(3, em.getPhone());
            ps.setString(4, em.getMail());
            ps.setString(5, em.getGender());
            ps.setString(6, em.getStatus());
            ps.setString(7, em.getAddress());
            ps.setInt(8, em.getId());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        try {
            String query = "DELETE FROM employee WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
