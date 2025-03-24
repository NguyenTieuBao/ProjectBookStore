/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.JDBC.JDBC;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tieu Bao
 */
public class AccountController {
    
    public boolean checkLogin(String username, String password) {
        boolean b = false;
        
        try {
            String query = "SELECT username, password "
                    + "FROM account "
                    + "WHERE username = '"+username+"' "
                    + "AND password = '"+password+"' ";
            
            Statement stmt = JDBC.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                b = true;
            } 
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    public void resetPass() {
        String username = "admin";
        String password = "admin";
        String hashPass = getSHA256Hash(password);
        
        String query = "UPDATE account SET username = ?, password = ? WHERE id = 1";
        
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, hashPass);
            
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public String readAccount() {
        String username = null;
        try {
            String query = "SELECT username FROM account WHERE id = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                username = rs.getString("username");
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
    
    public String readPass() {
        String password = null;
        try {
            String query = "SELECT password FROM account WHERE id = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                password = rs.getString("password");
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
    
    public void updatePass(String pass) {
        try {
            String query = "UPDATE account SET password = ? WHERE id = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, pass);
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getSHA256Hash(String password) {
        
        MessageDigest md;
        try {
            
            md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
        }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }  
    }
    
}
