
package com.controller.category;

import com.JDBC.JDBC;
import com.model.category.CategoryModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.BmpImage;
import java.io.FileOutputStream;


public class CategoryController {
    
    public void insert(CategoryModel categoryModel) {
        try {
            String query = "INSERT INTO category VALUES (null, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, categoryModel.getName());
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<CategoryModel> loadData() {
        List<CategoryModel> list = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM category";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                
                CategoryModel categoryModel = new CategoryModel(id, name);
                list.add(categoryModel);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"" + e);
        }
        
        return list;
    }
    
    public CategoryModel getIdTable(int id) {
        CategoryModel cm = null;
        
        try {
            String query = "SELECT * FROM category WHERE id = " + id;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer ids = rs.getInt("id");
                String name = rs.getString("name");
                
                cm = new CategoryModel(ids, name);
            }
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cm;
    }
    
    public void update(CategoryModel cm) {
        try {
            String query = "UPDATE category SET name = ? WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, cm.getName());
            ps.setInt(2, cm.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        try {
            String query = "DELETE FROM category WHERE id = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            JDBC.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void testGenPDF(){
        try {
            // create pdf document object
             Document document = new Document();
             // open output stream writer
             PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
             // open document
             document.open();
             
            String query = "SELECT * FROM category";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                document.add(new Paragraph("Column 1: " + id));
                document.add(new Paragraph("Column 2: " + name));
            }
            document.close();
            System.out.println("PDF generated successfully!");
            JDBC.getConnection().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"" + e);
        }
    }
}
