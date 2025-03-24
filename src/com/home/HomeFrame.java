/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.home;

import com.account.LoginFrame;
import com.controller.AccountController;
import com.controller.bill.BillController;
import com.controller.billDetail.BillDetailController;
import com.controller.book.BookController;
import com.controller.category.CategoryController;
import com.controller.customer.CustomerController;
import com.controller.employee.EmployeeController;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.BmpImage;
import com.model.bill.BillModel;
import com.model.billDetail.BillDetailModel;
import com.model.book.BookModel;
import com.model.category.CategoryModel;
import com.model.customer.CustomerModel;
import com.model.employee.EmployeeModel;
import com.view.book.EditBookDialog;
import com.view.book.InsertBookDialog;
import com.view.category.InsertCategoryDialog;
import com.view.category.UpdateCategoryDialog;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Tieu Bao
 */
public class HomeFrame extends javax.swing.JFrame {

    private List<CategoryModel> cm;
    private int id = 0;
    public double finalTotal = 0;
    
    public HomeFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
        loadTableCategory();
        loadTableBook();
        loadTableCustomer();
        loadTableEmployee();
        
        // Bill
        loadTableBillBook();
        loadTableBillCustomer();
        loadTableBillEmployee();
        
        loadTableBill();
        timeNowBill();
        dateNowBill();
        loadAccount();
    }
    
    public void loadDataCategory(List<CategoryModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblCategory.getModel();
        dtm.setRowCount(0);
        
        for (CategoryModel categoryModel : list) {
            Vector v = new Vector();
            v.add(categoryModel.getId());
            v.add(categoryModel.getName());
            dtm.addRow(v);
        }
        
    }
    
    public void loadTableCategory() {
        CategoryController cc = new CategoryController();
        List list = cc.loadData();
        loadDataCategory(list);
    }
    
    public void loadDataBook(List<BookModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblBook.getModel();
        dtm.setRowCount(0);
        
        for (BookModel bookModel : list) {
            Vector v = new Vector();
            v.add(bookModel.getId());
            v.add(bookModel.getIsbn());
            v.add(bookModel.getTitle());
            v.add(bookModel.getPublisher());
            v.add(bookModel.getPrice());
            v.add(bookModel.getQuantity());
            v.add(bookModel.getAuthor());
            v.add(bookModel.getDescription());
            v.add(bookModel.getStatus());
            v.add(bookModel.getCategoryId());
            dtm.addRow(v);
        }
    }
    
    public void loadTableBook() {
        BookController bc = new BookController();
        List list = bc.loadData();
        loadDataBook(list);
    }
    
    public void loadDataCustomer(List<CustomerModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblCustomer.getModel();
        dtm.setRowCount(0);
        
        for (CustomerModel customerModel : list) {
            Vector v = new Vector();
            v.add(customerModel.getId());
            v.add(customerModel.getName());
            v.add(customerModel.getDate());
            v.add(customerModel.getPhone());
            v.add(customerModel.getGender());
            v.add(customerModel.getAddress());
            dtm.addRow(v);
        }
    }
    
    public void loadTableCustomer() {
        CustomerController cc = new CustomerController();
        List list = cc.loadData();
        loadDataCustomer(list);
    }
    
    public void loadDataEmployee(List<EmployeeModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblEmployee.getModel();
        dtm.setRowCount(0);
        
        for (EmployeeModel employeeModel : list) {
            Vector v = new Vector();
            v.add(employeeModel.getId());
            v.add(employeeModel.getName());
            v.add(employeeModel.getDob());
            v.add(employeeModel.getPhone());
            v.add(employeeModel.getMail());
            v.add(employeeModel.getGender());
            v.add(employeeModel.getStatus());
            v.add(employeeModel.getAddress());
            dtm.addRow(v);
        }
    }
    
    public void loadTableEmployee() {
        EmployeeController ec = new EmployeeController();
        List list = ec.loadData();
        loadDataEmployee(list);
    }
    
    // Bill
    public void loadDataBill(List<BillModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblBill.getModel();
        dtm.setRowCount(0);
        
        for (BillModel billModel : list) {
            Vector v = new Vector();
            v.add(billModel.getId());
            v.add(billModel.getCustomerId());
            v.add(billModel.getTime());
            v.add(billModel.getDate());
            v.add(billModel.getEmployeeId());
            v.add(billModel.getTotal());
            dtm.addRow(v);
        }
    }
    
    public void loadTableBill() {
        BillController bc = new BillController();
        List list = bc.loadData();
        loadDataBill(list);
    }
    
    
    // Home
    public void dateNowBill() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.now();
        String formattedDate = date.format(formatter);
        dateTextBill.setText(formattedDate);
    }
    
    public void timeNowBill() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        String formattedTime = now.format(formatter);
        timeTextBill.setText(formattedTime);
    }
    
    public void loadDataBillBook(List<BookModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblBillBook.getModel();
        dtm.setRowCount(0);
        
        for (BookModel bookModel : list) {
            Vector v = new Vector();
            v.add(bookModel.getId());
            v.add(bookModel.getIsbn());
            v.add(bookModel.getTitle());
            v.add(bookModel.getPrice());
            v.add(bookModel.getQuantity());
            dtm.addRow(v);
        }
    }
    
    public void loadTableBillBook() {
        BookController bc = new BookController();
        List list = bc.loadData();
        loadDataBillBook(list);
    }
    
    public void loadDataBillCustomer(List<CustomerModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblBillCustomer.getModel();
        dtm.setRowCount(0);
        
        for (CustomerModel cm : list) {
            Vector v = new Vector();
            v.add(cm.getId());
            v.add(cm.getName());
            v.add(cm.getPhone());
            dtm.addRow(v);
        }
    }
    
    public void loadTableBillCustomer() {
        CustomerController cc = new CustomerController();
        List list = cc.loadData();
        loadDataBillCustomer(list);
    }
    
    public void loadDataBillEmployee(List<EmployeeModel> list) {
        DefaultTableModel dtm = (DefaultTableModel) tblBillEmployee.getModel();
        dtm.setRowCount(0);
        
        for (EmployeeModel em : list) {
            Vector v = new Vector();
            v.add(em.getId());
            v.add(em.getName());
            v.add(em.getPhone());
            dtm.addRow(v);
        }
    }
    
    public void loadTableBillEmployee() {
        EmployeeController ec = new EmployeeController();
        List list = ec.loadData();
        loadDataBillEmployee(list);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblBillBook = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblBillCustomer = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        billCustomerName = new javax.swing.JTextField();
        billCustomerPhone = new javax.swing.JTextField();
        textIdCustomer = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        dateTextBill = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        timeTextBill = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        employeeBillName = new javax.swing.JTextField();
        employeeBillPhone = new javax.swing.JTextField();
        textIdEmployee = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        bookTextISBN = new javax.swing.JTextField();
        bookTextTitle = new javax.swing.JTextField();
        textIdBook = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        textPriceBill = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        textQuantityBill = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        addBillBtn = new javax.swing.JButton();
        comboDiscountBill = new javax.swing.JComboBox<>();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblBillList = new javax.swing.JTable();
        btnAddNewBill = new javax.swing.JButton();
        idNewBill = new javax.swing.JTextField();
        btnAddBook = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblBillEmployee = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        finalTotalText = new javax.swing.JTextField();
        paidAmountText = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        returnAmountText = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        saveBillBtn = new javax.swing.JButton();
        deleteRowBill = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        deleteBtnBill = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        checkDateSearch = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnSearchBill = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        refreshBtnBill = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        addCategoryBtn = new javax.swing.JButton();
        deleteCategoryBtn = new javax.swing.JButton();
        editCategoryBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBook = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        textNameCustomer = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textPhoneCustomer = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textDobCustomer = new com.toedter.calendar.JDateChooser();
        boxGenderCustomer = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        textAddressCustomer = new javax.swing.JTextArea();
        btnInsertCustomer = new javax.swing.JButton();
        btnUpdateCustomer = new javax.swing.JButton();
        btnClearCustomer = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        textNameEmployee = new javax.swing.JTextField();
        textDobEmployee = new com.toedter.calendar.JDateChooser();
        textMailEmployee = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        textAddressEmployee = new javax.swing.JTextArea();
        textPhoneEmployee = new javax.swing.JTextField();
        comboGenderEmployee = new javax.swing.JComboBox<>();
        comboStatusEmployee = new javax.swing.JComboBox<>();
        btnInsertEmployee = new javax.swing.JButton();
        btnUpdateEmployee = new javax.swing.JButton();
        btnDeleteEmployee = new javax.swing.JButton();
        btnClearEmployee = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        textUserName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnUpdatePass = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel31.setText("jLabel31");

        jScrollPane12.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/book.png"))); // NOI18N
        jLabel36.setText("Book");

        tblBillBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "Title", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillBookMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblBillBook);
        if (tblBillBook.getColumnModel().getColumnCount() > 0) {
            tblBillBook.getColumnModel().getColumn(0).setResizable(false);
            tblBillBook.getColumnModel().getColumn(1).setResizable(false);
            tblBillBook.getColumnModel().getColumn(2).setResizable(false);
            tblBillBook.getColumnModel().getColumn(3).setResizable(false);
            tblBillBook.getColumnModel().getColumn(4).setResizable(false);
        }

        tblBillCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillCustomerMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblBillCustomer);
        if (tblBillCustomer.getColumnModel().getColumnCount() > 0) {
            tblBillCustomer.getColumnModel().getColumn(0).setResizable(false);
            tblBillCustomer.getColumnModel().getColumn(1).setResizable(false);
            tblBillCustomer.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/khachhang.png"))); // NOI18N
        jLabel37.setText("Customer");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/bill1.png"))); // NOI18N
        jLabel38.setText("Bill");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setText("Customer");

        billCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billCustomerName.setEnabled(false);

        billCustomerPhone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        billCustomerPhone.setEnabled(false);

        textIdCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(textIdCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(billCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billCustomerPhone)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(billCustomerPhone)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(billCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(textIdCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setText("Date");

        dateTextBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dateTextBill.setEnabled(false);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setText("Time");

        timeTextBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        timeTextBill.setEnabled(false);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateTextBill, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeTextBill)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dateTextBill, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(timeTextBill)
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Employee");

        employeeBillName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeeBillName.setEnabled(false);

        employeeBillPhone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeeBillPhone.setEnabled(false);

        textIdEmployee.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(textIdEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeBillName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeeBillPhone)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(employeeBillPhone)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(employeeBillName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(textIdEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Book");

        bookTextISBN.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        bookTextISBN.setEnabled(false);

        bookTextTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        bookTextTitle.setEnabled(false);

        textIdBook.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textIdBook, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookTextISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bookTextTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textIdBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bookTextISBN, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
            .addComponent(bookTextTitle)
        );

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setText("Price");

        textPriceBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textPriceBill.setEnabled(false);

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("Quantity");

        textQuantityBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textPriceBill, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textQuantityBill)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textPriceBill, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textQuantityBill)
        );

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setText("Discount");

        addBillBtn.setBackground(new java.awt.Color(102, 204, 0));
        addBillBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addBillBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBillBtn.setText("Add Bill");
        addBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBillBtnActionPerformed(evt);
            }
        });

        comboDiscountBill.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "5", "10", "20", "30", "40", "50" }));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(comboDiscountBill, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(addBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(addBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(comboDiscountBill)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tblBillList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "Name", "Price", "Discount", "Quantity", "Total", "ID Book"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillListMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tblBillList);
        if (tblBillList.getColumnModel().getColumnCount() > 0) {
            tblBillList.getColumnModel().getColumn(0).setResizable(false);
            tblBillList.getColumnModel().getColumn(1).setResizable(false);
            tblBillList.getColumnModel().getColumn(2).setResizable(false);
            tblBillList.getColumnModel().getColumn(3).setResizable(false);
            tblBillList.getColumnModel().getColumn(4).setResizable(false);
            tblBillList.getColumnModel().getColumn(5).setResizable(false);
            tblBillList.getColumnModel().getColumn(6).setResizable(false);
            tblBillList.getColumnModel().getColumn(7).setResizable(false);
        }

        btnAddNewBill.setBackground(new java.awt.Color(0, 204, 204));
        btnAddNewBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddNewBill.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNewBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        btnAddNewBill.setText("New Bill");
        btnAddNewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewBillActionPerformed(evt);
            }
        });

        idNewBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        idNewBill.setEnabled(false);

        btnAddBook.setBackground(new java.awt.Color(0, 153, 153));
        btnAddBook.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddBook.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        btnAddBook.setText("Add Book");
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane15))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddNewBill)
                        .addGap(28, 28, 28)
                        .addComponent(idNewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddBook)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddNewBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idNewBill))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/employeee.png"))); // NOI18N
        jLabel42.setText("Employee");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );

        tblBillEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillEmployeeMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tblBillEmployee);
        if (tblBillEmployee.getColumnModel().getColumnCount() > 0) {
            tblBillEmployee.getColumnModel().getColumn(0).setResizable(false);
            tblBillEmployee.getColumnModel().getColumn(1).setResizable(false);
            tblBillEmployee.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setText("Calculation Detail");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("Total");

        finalTotalText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        finalTotalText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalTotalTextActionPerformed(evt);
            }
        });

        paidAmountText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        paidAmountText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidAmountTextActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setText("Paid Amount");

        returnAmountText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        returnAmountText.setEnabled(false);

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setText("Return Amount");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(finalTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(finalTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnAmountText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        saveBillBtn.setBackground(new java.awt.Color(51, 204, 0));
        saveBillBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        saveBillBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveBillBtn.setText("Save");
        saveBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBillBtnActionPerformed(evt);
            }
        });

        deleteRowBill.setBackground(new java.awt.Color(204, 204, 0));
        deleteRowBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        deleteRowBill.setForeground(new java.awt.Color(255, 255, 255));
        deleteRowBill.setText("Delete Book");
        deleteRowBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRowBillActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(0, 153, 204));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Export");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        deleteBtnBill.setBackground(new java.awt.Color(204, 0, 0));
        deleteBtnBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        deleteBtnBill.setForeground(new java.awt.Color(255, 255, 255));
        deleteBtnBill.setText("Delete Bill");
        deleteBtnBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteRowBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteRowBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Home", jPanel9);

        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer Name", "Time", "Date", "Emoloyee", "Total price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBill);
        if (tblBill.getColumnModel().getColumnCount() > 0) {
            tblBill.getColumnModel().getColumn(0).setResizable(false);
            tblBill.getColumnModel().getColumn(1).setResizable(false);
            tblBill.getColumnModel().getColumn(2).setResizable(false);
            tblBill.getColumnModel().getColumn(3).setResizable(false);
            tblBill.getColumnModel().getColumn(4).setResizable(false);
            tblBill.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Bill by day");

        btnSearchBill.setBackground(new java.awt.Color(0, 204, 51));
        btnSearchBill.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearchBill.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/loupe.png"))); // NOI18N
        btnSearchBill.setText("Search");
        btnSearchBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchBillActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(17, 161, 225));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        refreshBtnBill.setBackground(new java.awt.Color(0, 204, 204));
        refreshBtnBill.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        refreshBtnBill.setForeground(new java.awt.Color(255, 255, 255));
        refreshBtnBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/refresh.png"))); // NOI18N
        refreshBtnBill.setText("Refresh");
        refreshBtnBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(213, 213, 213)
                        .addComponent(checkDateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnSearchBill, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshBtnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchBill, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(checkDateSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(refreshBtnBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Bill", jPanel2);

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCategory.setRowHeight(30);
        tblCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCategory);
        if (tblCategory.getColumnModel().getColumnCount() > 0) {
            tblCategory.getColumnModel().getColumn(0).setResizable(false);
            tblCategory.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/category.png"))); // NOI18N
        jLabel3.setText("Category");

        addCategoryBtn.setBackground(new java.awt.Color(0, 204, 51));
        addCategoryBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addCategoryBtn.setForeground(new java.awt.Color(255, 255, 255));
        addCategoryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        addCategoryBtn.setText("Insert Category");
        addCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryBtnActionPerformed(evt);
            }
        });

        deleteCategoryBtn.setBackground(new java.awt.Color(204, 0, 0));
        deleteCategoryBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteCategoryBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteCategoryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/trash.png"))); // NOI18N
        deleteCategoryBtn.setText("Delete Category");
        deleteCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryBtnActionPerformed(evt);
            }
        });

        editCategoryBtn.setBackground(new java.awt.Color(204, 204, 0));
        editCategoryBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editCategoryBtn.setForeground(new java.awt.Color(255, 255, 255));
        editCategoryBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/edit.png"))); // NOI18N
        editCategoryBtn.setText("Edit Category");
        editCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCategoryBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/book.png"))); // NOI18N
        jLabel4.setText("Book");

        tblBook.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "Title", "Pulisher", "Price", "Quantity", "Author", "Description", "Status", "Category Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBook.setRowHeight(30);
        jScrollPane3.setViewportView(tblBook);
        if (tblBook.getColumnModel().getColumnCount() > 0) {
            tblBook.getColumnModel().getColumn(0).setResizable(false);
            tblBook.getColumnModel().getColumn(1).setResizable(false);
            tblBook.getColumnModel().getColumn(2).setResizable(false);
            tblBook.getColumnModel().getColumn(3).setResizable(false);
            tblBook.getColumnModel().getColumn(4).setResizable(false);
            tblBook.getColumnModel().getColumn(5).setResizable(false);
            tblBook.getColumnModel().getColumn(6).setResizable(false);
            tblBook.getColumnModel().getColumn(7).setResizable(false);
            tblBook.getColumnModel().getColumn(8).setResizable(false);
            tblBook.getColumnModel().getColumn(9).setResizable(false);
        }

        jButton9.setBackground(new java.awt.Color(0, 204, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        jButton9.setText("Insert Book");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(204, 0, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/trash.png"))); // NOI18N
        jButton10.setText("Delete Book");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(204, 204, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/edit.png"))); // NOI18N
        jButton11.setText("Edit Book");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/refresh.png"))); // NOI18N
        jButton2.setText("Refesher");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(0, 204, 204));
        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/refresh.png"))); // NOI18N
        jButton17.setText("Refesher");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(addCategoryBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editCategoryBtn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11)
                        .addGap(18, 18, 18)
                        .addComponent(jButton10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(deleteCategoryBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCategoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editCategoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(deleteCategoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Book", jPanel3);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/khachhang.png"))); // NOI18N
        jLabel5.setText("Customer");

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Date of birth", "Phone", "Gender", "Address"
            }
        ));
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCustomer);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Name: ");

        textNameCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Date of birth:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Phone:");

        textPhoneCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Gender:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Address:");

        textDobCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        boxGenderCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        boxGenderCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choice gender", "Male", "Female" }));

        textAddressCustomer.setColumns(20);
        textAddressCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textAddressCustomer.setRows(5);
        jScrollPane5.setViewportView(textAddressCustomer);

        btnInsertCustomer.setBackground(new java.awt.Color(51, 204, 0));
        btnInsertCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnInsertCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        btnInsertCustomer.setText("Insert Customer");
        btnInsertCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertCustomerActionPerformed(evt);
            }
        });

        btnUpdateCustomer.setBackground(new java.awt.Color(204, 204, 0));
        btnUpdateCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdateCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/edit.png"))); // NOI18N
        btnUpdateCustomer.setText("Update Customer");
        btnUpdateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCustomerActionPerformed(evt);
            }
        });

        btnClearCustomer.setBackground(new java.awt.Color(0, 204, 204));
        btnClearCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClearCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnClearCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/clean.png"))); // NOI18N
        btnClearCustomer.setText("Clear Text");
        btnClearCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCustomerActionPerformed(evt);
            }
        });

        btnDeleteCustomer.setBackground(new java.awt.Color(255, 51, 0));
        btnDeleteCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDeleteCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/trash.png"))); // NOI18N
        btnDeleteCustomer.setText("Delete Customer");
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(boxGenderCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textNameCustomer)
                                    .addComponent(textDobCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(textPhoneCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInsertCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdateCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClearCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(textDobCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textPhoneCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(boxGenderCustomer))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsertCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClearCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Customer", jPanel4);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/employeee.png"))); // NOI18N
        jLabel11.setText("Employee");

        tblEmployee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Date of birth", "Phone", "Mail", "Gender", "Status", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblEmployee);
        if (tblEmployee.getColumnModel().getColumnCount() > 0) {
            tblEmployee.getColumnModel().getColumn(0).setResizable(false);
            tblEmployee.getColumnModel().getColumn(1).setResizable(false);
            tblEmployee.getColumnModel().getColumn(2).setResizable(false);
            tblEmployee.getColumnModel().getColumn(3).setResizable(false);
            tblEmployee.getColumnModel().getColumn(4).setResizable(false);
            tblEmployee.getColumnModel().getColumn(5).setResizable(false);
            tblEmployee.getColumnModel().getColumn(6).setResizable(false);
            tblEmployee.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel12.setText("Name:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel13.setText("Date of birth:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Phone:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Mail:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel16.setText("Gender:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel17.setText("Status:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setText("Address:");

        textNameEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        textDobEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        textMailEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        textAddressEmployee.setColumns(20);
        textAddressEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        textAddressEmployee.setRows(5);
        jScrollPane7.setViewportView(textAddressEmployee);

        textPhoneEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        comboGenderEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboGenderEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choice gender", "Male", "Female" }));

        comboStatusEmployee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboStatusEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choice status", "Active", "Negative" }));

        btnInsertEmployee.setBackground(new java.awt.Color(102, 204, 0));
        btnInsertEmployee.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnInsertEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/more.png"))); // NOI18N
        btnInsertEmployee.setText("Insert");
        btnInsertEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertEmployeeActionPerformed(evt);
            }
        });

        btnUpdateEmployee.setBackground(new java.awt.Color(204, 204, 0));
        btnUpdateEmployee.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnUpdateEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/edit.png"))); // NOI18N
        btnUpdateEmployee.setText("Update");
        btnUpdateEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateEmployeeActionPerformed(evt);
            }
        });

        btnDeleteEmployee.setBackground(new java.awt.Color(204, 51, 0));
        btnDeleteEmployee.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDeleteEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/trash.png"))); // NOI18N
        btnDeleteEmployee.setText("Delete");
        btnDeleteEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteEmployeeActionPerformed(evt);
            }
        });

        btnClearEmployee.setBackground(new java.awt.Color(0, 204, 204));
        btnClearEmployee.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnClearEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnClearEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/clean.png"))); // NOI18N
        btnClearEmployee.setText("Clear Text");
        btnClearEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearEmployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textNameEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textDobEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textPhoneEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textMailEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboGenderEmployee, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboStatusEmployee, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnInsertEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdateEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnClearEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDeleteEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNameEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textDobEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPhoneEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textMailEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboGenderEmployee)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(comboStatusEmployee))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInsertEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClearEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
        );

        jTabbedPane1.addTab("Employee", jPanel5);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 204, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Change Password Book Store Manager");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("User Name:");

        textUserName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        textUserName.setEnabled(false);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Current Password:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("New Password:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Confirm Password:");

        btnUpdatePass.setBackground(new java.awt.Color(0, 204, 0));
        btnUpdatePass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdatePass.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdatePass.setText("Update");
        btnUpdatePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePassActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 204, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textUserName)
                                    .addComponent(jPasswordField1))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(btnUpdatePass, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(60, 60, 60)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel24Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jPasswordField3))))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordField2)))))
                .addGap(75, 75, 75))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(90, 90, 90)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUpdatePass, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 821, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Setting", jPanel7);

        jPanel1.setBackground(new java.awt.Color(17, 161, 225));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/homepage.png"))); // NOI18N
        jLabel1.setText("Book Store Manager");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/image/out.png"))); // NOI18N
        jButton1.setText("LogOut");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnClearEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearEmployeeActionPerformed
        textNameEmployee.setText("");
        textDobEmployee.setDate(null);
        textPhoneEmployee.setText("");
        textMailEmployee.setText("");
        comboGenderEmployee.setSelectedItem("Choice gender");
        comboStatusEmployee.setSelectedItem("Choice status");
        textAddressEmployee.setText("");
    }//GEN-LAST:event_btnClearEmployeeActionPerformed

    private void btnDeleteEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteEmployeeActionPerformed
        EmployeeController ec = new EmployeeController();
        int option = JOptionPane.showConfirmDialog(null,
            "You Want Delete This Employee ?", "Yes",
            JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            ec.delete(id);
            JOptionPane.showMessageDialog(null,
                "Delete Successfully.");
            textNameEmployee.setText("");
            textDobEmployee.setDate(null);
            textPhoneEmployee.setText("");
            textMailEmployee.setText("");
            comboGenderEmployee.setSelectedItem("Choice gender");
            comboStatusEmployee.setSelectedItem("Choice status");
            textAddressEmployee.setText("");
        } else if (option == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null,
                "You selected No.");
        }

        loadTableEmployee();
    }//GEN-LAST:event_btnDeleteEmployeeActionPerformed

    private void btnUpdateEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateEmployeeActionPerformed

        String name = textNameEmployee.getText();
        Date dob = textDobEmployee.getDate();
        String phone = textPhoneEmployee.getText();
        String mail = textMailEmployee.getText();
        String gender = (String) comboGenderEmployee.getSelectedItem();
        String status = (String) comboStatusEmployee.getSelectedItem();
        String address = textAddressEmployee.getText();

        EmployeeModel em = new EmployeeModel(id, name, dob, phone, mail, gender, status, address);
        EmployeeController ec = new EmployeeController();

        int option = JOptionPane.showConfirmDialog(null,
            "You Want Update This Employee ?", "Yes",
            JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Kiểm tra địa chỉ email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!mail.matches(emailRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid email address, try again!");
                return;
            }

            // Kiểm tra số điện thoại
            String phoneRegex = "^\\d{10}$";
            if (!phone.matches(phoneRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number, try again!");
                return;
            }
            ec.update(em);

            JOptionPane.showMessageDialog(null,
                "Update Successfully.");
            textNameEmployee.setText("");
            textDobEmployee.setDate(null);
            textPhoneEmployee.setText("");
            textMailEmployee.setText("");
            comboGenderEmployee.setSelectedItem("Choice gender");
            comboStatusEmployee.setSelectedItem("Choice status");
            textAddressEmployee.setText("");
        } else if (option == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null,
                "You selected No.");
        }

        loadTableEmployee();
    }//GEN-LAST:event_btnUpdateEmployeeActionPerformed

    private void btnInsertEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertEmployeeActionPerformed

        String name = textNameEmployee.getText();
        Date dob = textDobEmployee.getDate();
        String phone = textPhoneEmployee.getText();
        String mail = textMailEmployee.getText();
        String gender = (String) comboGenderEmployee.getSelectedItem();
        String status = (String) comboStatusEmployee.getSelectedItem();
        String address = textAddressEmployee.getText();

        EmployeeModel em = new EmployeeModel(null, name, dob, phone, mail, gender, status, address);
        EmployeeController ec = new EmployeeController();

        int option = JOptionPane.showConfirmDialog(null,
            "You Want Insert This Employee ?", "Yes",
            JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Kiểm tra địa chỉ email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!mail.matches(emailRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid email address, try again!");
                return;
            }

            // Kiểm tra số điện thoại
            String phoneRegex = "^\\d{10}$";
            if (!phone.matches(phoneRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number, try again!");
                return;
            }
            ec.insert(em);
            JOptionPane.showMessageDialog(null,
                "Insert Successfully.");
            textNameEmployee.setText("");
            textDobEmployee.setDate(null);
            textPhoneEmployee.setText("");
            textMailEmployee.setText("");
            comboGenderEmployee.setSelectedItem("Choice gender");
            comboStatusEmployee.setSelectedItem("Choice status");
            textAddressEmployee.setText("");
        } else if (option == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null,
                "You selected No.");
        }
        loadTableEmployee();
    }//GEN-LAST:event_btnInsertEmployeeActionPerformed

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        int row = tblEmployee.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Please select any record from table");
        } else {
            id = (int) tblEmployee.getValueAt(row, 0);
            EmployeeController ec = new EmployeeController();
            EmployeeModel em = ec.getTableId(id);
            textNameEmployee.setText(em.getName());
            textDobEmployee.setDate(em.getDob());
            textPhoneEmployee.setText(em.getPhone());
            textMailEmployee.setText(em.getMail());
            comboGenderEmployee.setSelectedItem(em.getGender());
            comboStatusEmployee.setSelectedItem(em.getStatus());
            textAddressEmployee.setText(em.getAddress());
        }
    }//GEN-LAST:event_tblEmployeeMouseClicked

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        int row = tblCustomer.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for edit.");
        } else {
            CustomerController cc = new CustomerController();
            int option = JOptionPane.showConfirmDialog(null,
                "You Want Delete This Customer ?", "Yes",
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                cc.delete(id);
                JOptionPane.showMessageDialog(null,
                    "Delete Successfully.");
                textNameCustomer.setText("");
                textDobCustomer.setDate(null);
                textPhoneCustomer.setText("");
                textAddressCustomer.setText("");
                boxGenderCustomer.setSelectedItem("Choice gender");
            } else if (option == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null,
                    "You selected No.");
                textNameCustomer.setText("");
                textDobCustomer.setDate(null);
                textPhoneCustomer.setText("");
                textAddressCustomer.setText("");
                boxGenderCustomer.setSelectedItem("Choice gender");
            }

            loadTableCustomer();
        }
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void btnClearCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCustomerActionPerformed
        textNameCustomer.setText("");
        textDobCustomer.setDate(null);
        textPhoneCustomer.setText("");
        textAddressCustomer.setText("");
        boxGenderCustomer.setSelectedItem("Choice gender");
    }//GEN-LAST:event_btnClearCustomerActionPerformed

    private void btnUpdateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCustomerActionPerformed
        String name = textNameCustomer.getText();
        Date dob = textDobCustomer.getDate();
        String phone = textPhoneCustomer.getText();
        String address =  textAddressCustomer.getText();
        String gender = (String) boxGenderCustomer.getSelectedItem();

        CustomerModel cm = new CustomerModel(id, name, dob, phone, address, gender);

        CustomerController cc = new CustomerController();
        int row = tblCustomer.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for edit.");
        } else {
            int option = JOptionPane.showConfirmDialog(null,
                "You Want Update This Customer?",
                "Yes", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Kiểm tra số điện thoại
                String phoneRegex = "^\\d{10}$";
                if (!phone.matches(phoneRegex)) {
                    JOptionPane.showMessageDialog(null, "Invalid phone number, try again!");
                    return;
                }
                cc.update(cm);
                JOptionPane.showMessageDialog(null,
                    "Update Successfully.");
                textNameCustomer.setText("");
                textDobCustomer.setDate(null);
                textPhoneCustomer.setText("");
                textAddressCustomer.setText("");
                boxGenderCustomer.setSelectedItem("Choice gender");
            }
            loadTableCustomer();
        }
    }//GEN-LAST:event_btnUpdateCustomerActionPerformed

    private void btnInsertCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertCustomerActionPerformed
        String name = textNameCustomer.getText();
        Date dob = textDobCustomer.getDate();
        String phone = textPhoneCustomer.getText();
        String address =  textAddressCustomer.getText();
        String gender = (String) boxGenderCustomer.getSelectedItem();

        CustomerModel cm = new CustomerModel(null, name, dob, phone, address, gender);

        CustomerController cc = new CustomerController();

        int option = JOptionPane.showConfirmDialog(null,
            "You Want Insert This Customer?",
            "Yes", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {

            // Kiểm tra số điện thoại
            String phoneRegex = "^\\d{10}$";
            if (!phone.matches(phoneRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number, try again!");
                return;
            }

            cc.insert(cm);
            JOptionPane.showMessageDialog(null,
                "Insert Successfully.");
            textNameCustomer.setText("");
            textDobCustomer.setDate(null);
            textPhoneCustomer.setText("");
            textAddressCustomer.setText("");
            boxGenderCustomer.setSelectedItem("Choice gender");

        }
        loadTableCustomer();
    }//GEN-LAST:event_btnInsertCustomerActionPerformed

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        int row = tblCustomer.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for update.");
        } else {
            id = (int) tblCustomer.getValueAt(row, 0);
            CustomerController cc = new CustomerController();
            CustomerModel cm = cc.getIdTable(id);
            textNameCustomer.setText(cm.getName());
            textDobCustomer.setDate(cm.getDate());
            textPhoneCustomer.setText(cm.getPhone());
            textAddressCustomer.setText(cm.getAddress());
            boxGenderCustomer.setSelectedItem(cm.getGender());
        }
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        loadTableCategory();
        //        CategoryController cc = new CategoryController();
        //        cc.testGenPDF();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadTableBook();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int row = tblBook.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for edit.");
        } else {
            id = (int) tblBook.getValueAt(row, 0);
            BookController bc = new BookController();
            BookModel bm = bc.getIdTable(id);
            EditBookDialog edit = new EditBookDialog(this, rootPaneCheckingEnabled);
            edit.setEditData(bm);

            edit.setVisible(true);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        BookController bc = new BookController();
        int row = tblBook.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for delete.");
        } else {
            id = (int) tblBook.getValueAt(row, 0);
            CategoryController cc = new CategoryController();
            int option = JOptionPane.showConfirmDialog(null,
                "You Want Delete This Book ?", "Yes",
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                
                boolean check = bc.delete(id);
                if (check == true) {
                    JOptionPane.showMessageDialog(null,
                    "Delete Successfully.");
                    
                } else {
                    JOptionPane.showMessageDialog(null,
                    "Deleting the book failed because there is already a purchase receipt for this book");
                    return;
                }
                
            } else if (option == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null,
                    "You selected No.");
            }

            loadTableBook();
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        InsertBookDialog addBook = new InsertBookDialog(this, rootPaneCheckingEnabled);
        addBook.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void editCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCategoryBtnActionPerformed
        int row = tblCategory.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for edit.");

        } else {
            id = (int) tblCategory.getValueAt(row, 0);
            CategoryController cc = new CategoryController();
            CategoryModel cm = cc.getIdTable(id);
            UpdateCategoryDialog edit = new UpdateCategoryDialog(this, rootPaneCheckingEnabled);
            edit.setEditData(cm);

            edit.setVisible(true);
        }
    }//GEN-LAST:event_editCategoryBtnActionPerformed

    private void deleteCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryBtnActionPerformed
        
        int row = tblCategory.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null,
                "Please select any record from table for delete.");
        } else {
            id = (int) tblCategory.getValueAt(row, 0);
            CategoryController cc = new CategoryController();
            int option = JOptionPane.showConfirmDialog(null,
                "You Want Delete This Category ?", "Yes",
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                cc.delete(id);
                JOptionPane.showMessageDialog(null,
                    "Delete Successfully.");
            } else if (option == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null,
                    "You selected No.");
            }

            loadTableCategory();
        }
    }//GEN-LAST:event_deleteCategoryBtnActionPerformed

    private void addCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryBtnActionPerformed
        InsertCategoryDialog add = new InsertCategoryDialog(this, rootPaneCheckingEnabled);
        add.setVisible(true);
    }//GEN-LAST:event_addCategoryBtnActionPerformed

    private void tblCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoryMouseClicked

    }//GEN-LAST:event_tblCategoryMouseClicked

    private void tblBillBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillBookMouseClicked
        BookController bc = new BookController();
        int row = tblBillBook.getSelectedRow();
        if (row >= 0) {
            id = (int) tblBillBook.getValueAt(row, 0);
            BookModel bm = bc.getIdTable(id);
            textIdBook.setText(bm.getId() + "");
            bookTextISBN.setText(bm.getIsbn());
            bookTextTitle.setText(bm.getTitle());
            textPriceBill.setText(bm.getPrice() + "");
            textQuantityBill.setText("1"); 
        }
    }//GEN-LAST:event_tblBillBookMouseClicked
 
    private void tblBillCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillCustomerMouseClicked
        CustomerController cc = new CustomerController();
        int row = tblBillCustomer.getSelectedRow();
        if (row >= 0) {
            id = (int) tblBillCustomer.getValueAt(row, 0);
            CustomerModel cm = cc.getIdTable(id);
            textIdCustomer.setText(cm.getId() + "");
            billCustomerName.setText(cm.getName());
            billCustomerPhone.setText(cm.getPhone());
        }
    }//GEN-LAST:event_tblBillCustomerMouseClicked

    private void tblBillEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillEmployeeMouseClicked
        EmployeeController ec = new EmployeeController();
        int row = tblBillEmployee.getSelectedRow();
        if (row >= 0) {
            id = (int) tblBillEmployee.getValueAt(row, 0);
            EmployeeModel em = ec.getTableId(id);
            textIdEmployee.setText(em.getId() + "");
            employeeBillName.setText(em.getName());
            employeeBillPhone.setText(em.getPhone());
        }
    }//GEN-LAST:event_tblBillEmployeeMouseClicked

    private void addBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillBtnActionPerformed
        String idBook = textIdBook.getText();
        String isbnBook = bookTextISBN.getText();
        String titleBook = bookTextTitle.getText();
        String idNewBillText = idNewBill.getText();
        Double priceBook;
        Double quantityBook;
        Double discountBook;
        
        try {
            priceBook = Double.parseDouble(textPriceBill.getText());
            quantityBook = Double.parseDouble(textQuantityBill.getText());
            discountBook = Double.parseDouble((String) comboDiscountBill.getSelectedItem());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            return;
        }
        
//        double z = priceBook * quantityBook * discountBook / 100;
//        double total = (priceBook * quantityBook) - z;
        
        if (idBook.equals("") && isbnBook.equals("") 
                && titleBook.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Book.");
            
        } else if (textIdCustomer.getText().isEmpty() 
                && billCustomerName.getText().isEmpty() 
                && billCustomerPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Customer.");
            
        } else if (textIdEmployee.getText().isEmpty() 
                && employeeBillName.getText().isEmpty()
                && employeeBillPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Employee.");
            
        } else if (idNewBillText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please add new Bill.");
            
        } else {
            int option = JOptionPane.showConfirmDialog(null, 
                    "You Want Add This Bill?", "Yes",
                    JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                int idBills;
                try {
                    idBills = Integer.parseInt(idNewBill.getText());
                    String idCustomer = textIdCustomer.getText();
                    String idEmployee = textIdEmployee.getText();

                    DateTimeFormatter timeText = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime time = LocalTime.parse(timeTextBill.getText(), timeText);

                    DateTimeFormatter dateText = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse(dateTextBill.getText(), dateText);

                    BillModel bm = new BillModel(idBills, idCustomer, time, date, idEmployee, 0);

                    BillController bc = new BillController();
                    bc.insert(bm);
                } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for Bill ID.");
                
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid date and time.");

                }
            }
                
        }  
    }//GEN-LAST:event_addBillBtnActionPerformed

    private void paidAmountTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidAmountTextActionPerformed
        double paidAmount = Double.parseDouble(paidAmountText.getText());
        
        double z = Double.parseDouble(finalTotalText.getText());
        double returnPaidAmount = paidAmount - z;
        returnAmountText.setText(returnPaidAmount + "");
    }//GEN-LAST:event_paidAmountTextActionPerformed

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }   
        
    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // Bill
        loadTableBillBook();
        loadTableBillCustomer();
        loadTableBillEmployee();
        
        // Date and Time
        dateNowBill();
        timeNowBill();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tblBillListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillListMouseClicked
        
    }//GEN-LAST:event_tblBillListMouseClicked

    private void deleteRowBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowBillActionPerformed
        int option = JOptionPane.showConfirmDialog(null, 
                    "You Want Delete This Book In The Table?", "Yes",
                    JOptionPane.YES_NO_OPTION);
            
        if (option == JOptionPane.YES_OPTION) {
        
            BillDetailController bdc = new BillDetailController(); 
            int row = tblBillList.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, 
                        "Please select any record from table bill.");
            }
            else if (row >= 0) {
                id = (int) tblBillList.getValueAt(row, 0);
                int bookId = (int) tblBillList.getValueAt(row, 7);
                double quantity = (double) tblBillList.getValueAt(row, 5);
                double totalDelete = (double) tblBillList.getValueAt(row, 6);
                bdc.deleteById(id, bookId, quantity);
                 // Xóa dòng từ JTable
                DefaultTableModel model = (DefaultTableModel) tblBillList.getModel();
//                double z = finalTotal;
                finalTotal = finalTotal - totalDelete;
                finalTotalText.setText(finalTotal + "");

                model.removeRow(row);  
                loadTableBillBook();
            }
        }
    }//GEN-LAST:event_deleteRowBillActionPerformed

    private void saveBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBillBtnActionPerformed
        int option = JOptionPane.showConfirmDialog(null, 
                "You Want Save This Bill?", "Yes", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            int idBills = Integer.parseInt(idNewBill.getText());
            BillController bc = new BillController();
            double totalPrice = bc.calculateTotalPrice(idBills);
            bc.updateTotal(totalPrice, idBills);
            
            
            idNewBill.setText("");
            textIdCustomer.setText("");
            billCustomerName.setText("");
            billCustomerPhone.setText("");
            // Date and Time
            dateNowBill();
            timeNowBill();
            textIdEmployee.setText("");
            employeeBillName.setText("");
            employeeBillPhone.setText("");
            textIdBook.setText("");
            bookTextISBN.setText("");
            bookTextTitle.setText("");
            textPriceBill.setText("");
            textQuantityBill.setText("");
            comboDiscountBill.setSelectedItem("0");
            finalTotal = 0;
            finalTotalText.setText("");
            DefaultTableModel dtm = (DefaultTableModel) tblBillList.getModel();
            dtm.setRowCount(0);
        }
        
    }//GEN-LAST:event_saveBillBtnActionPerformed

    private int idBill = 0;
    private void btnAddNewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewBillActionPerformed
        
        BillController bc = new BillController();
        idBill = bc.getLatestBillId();
        idBill++;
        
        idNewBill.setText(String.valueOf(idBill));
    }//GEN-LAST:event_btnAddNewBillActionPerformed
    
    
    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookActionPerformed
        
        String idBook = textIdBook.getText();
        String isbnBook = bookTextISBN.getText();
        String titleBook = bookTextTitle.getText();
        String idNewBillText = idNewBill.getText();
        Double priceBook;
        Double quantityBook;
        Double discountBook;
        
        try {
            priceBook = Double.parseDouble(textPriceBill.getText());
            quantityBook = Double.parseDouble(textQuantityBill.getText());
            discountBook = Double.parseDouble((String) comboDiscountBill.getSelectedItem());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            return;
        }
        
        
        
        if (idBook.equals("") && isbnBook.equals("") 
                && titleBook.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Book.");
            
        } else if (textIdCustomer.getText().isEmpty() 
                && billCustomerName.getText().isEmpty() 
                && billCustomerPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Customer.");
            
        } else if (textIdEmployee.getText().isEmpty() 
                && employeeBillName.getText().isEmpty()
                && employeeBillPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select any record from table Employee.");
            
        } else if (idNewBillText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please add new Bill.");
        } else {
            int idBills;
            int idBooks;
            
            double z = priceBook * quantityBook * discountBook / 100;
            double total = (priceBook * quantityBook) - z;
            try {
                idBills = Integer.parseInt(idNewBill.getText());
                idBooks = Integer.parseInt(idBook);
                
                BillController bc = new BillController();
            
                if (bc.searchId(idBills) == true) {
                    BillDetailController bdc = new BillDetailController();
                    
                    BillDetailModel bdm = new BillDetailModel(null, idBooks, idBills, total, quantityBook, discountBook);
                    bdc.insert(bdm);

                    DefaultTableModel dtm = (DefaultTableModel) tblBillList.getModel();
                    dtm.addRow(new Object[]{
                            bdc.readIdBillDetail(), 
                        isbnBook, titleBook, 
                        priceBook, discountBook, 
                        quantityBook, total, idBooks
                    });

                    
                    finalTotal = finalTotal + total;
                    finalTotalText.setText(String.valueOf(finalTotal));
                    
                    loadTableBillBook();
                } else {
                    JOptionPane.showMessageDialog(null, "You haven't clicked add bill, try again!");
                }
                
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for Bill ID.");
                
            } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid date and time.");
            }   
        } 
    }//GEN-LAST:event_btnAddBookActionPerformed

    private void deleteBtnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnBillActionPerformed
        int option = JOptionPane.showConfirmDialog(null, 
                "You Want Save This Bill?", "Yes", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
        
            int idBills = Integer.parseInt(idNewBill.getText());
            BillController bc = new BillController();
            BillDetailController bdc = new BillDetailController();
            
            int row = (int) tblBillList.getRowCount();

            for (int i = 0; i < row; i++) {
                int bookId = (int) tblBillList.getValueAt(i, 7);
                double quantity = (double) tblBillList.getValueAt(i, 5);
                
                bdc.deleteById(i, bookId, quantity);
            }
 
            bc.delete(idBills);

            loadTableBillBook();
            
            idNewBill.setText("");
            textIdCustomer.setText("");
            billCustomerName.setText("");
            billCustomerPhone.setText("");
            timeNowBill();
            dateNowBill();
            textIdEmployee.setText("");
            employeeBillName.setText("");
            employeeBillPhone.setText("");
            textIdBook.setText("");
            bookTextISBN.setText("");
            bookTextTitle.setText("");
            textPriceBill.setText("");
            textQuantityBill.setText("");
            comboDiscountBill.setSelectedItem("0");
            finalTotalText.setText("");
            DefaultTableModel dtm = (DefaultTableModel) tblBillList.getModel();
            dtm.setRowCount(0);
        }
    }//GEN-LAST:event_deleteBtnBillActionPerformed

    private void btnSearchBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchBillActionPerformed
//        Date date = checkDateSearch.getDate();
        Date utilDate = checkDateSearch.getDate();
        LocalDate date = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        BillController bc = new BillController();
        bc.loadDataDate(date);
        loadTableBillDate();
    }//GEN-LAST:event_btnSearchBillActionPerformed

    private void refreshBtnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnBillActionPerformed
        loadTableBill();;
    }//GEN-LAST:event_refreshBtnBillActionPerformed

    private void btnUpdatePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePassActionPerformed
        AccountController ac = new AccountController();
        String pass = ac.readPass();
        String passText = jPasswordField1.getText();
        String passSHA = ac.getSHA256Hash(passText);
        
        String newPass = jPasswordField2.getText();
        String newPassSHA = ac.getSHA256Hash(newPass);
        
        String confirmPass = jPasswordField3.getText();
        String confirmPassSHA = ac.getSHA256Hash(confirmPass);
        
        if (!pass.equals(passSHA)) {
            JOptionPane.showMessageDialog(null, "Password false, try again!");
        } else if (!newPassSHA.equals(confirmPassSHA)) {
            JOptionPane.showMessageDialog(null, "The new password and the confirm password are not the same, try again!");
        } else {
            ac.updatePass(confirmPassSHA);
            JOptionPane.showMessageDialog(null, "Xong"); 
        }
    }//GEN-LAST:event_btnUpdatePassActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        
        int option = JOptionPane.showConfirmDialog(null, 
                "You Want Export This Bill?", "Yes", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            int idBills = Integer.parseInt(idNewBill.getText());
            BillController bc = new BillController();
            double totalPrice = bc.calculateTotalPrice(idBills);
            bc.updateTotal(totalPrice, idBills);
            
            String nameCustomer = billCustomerName.getText();
            String phoneCustomer = billCustomerPhone.getText();
            String path = "";
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream(path + "" +nameCustomer+" "+dateTextBill.getText()+".pdf"));
                doc.open();
                Paragraph paragraph1 = new Paragraph(" ------------------------------------------ BILL BOOK STORE MANAGER ------------------------------------------\n\n"
                        + "Contact Number: (+84)971047870\n");
                doc.add(paragraph1);
                Paragraph paragraph2 = new Paragraph("Date & Time: " + dateTextBill.getText() + " " + timeTextBill.getText() + "\n\nCustomer Name: " +nameCustomer+"\n\n" + "Customer Phone: " +phoneCustomer+ "\n\n\n");
                doc.add(paragraph2);
                PdfPTable tbl = new PdfPTable(8);
                tbl.addCell("ID");
                tbl.addCell("ISBN");
                tbl.addCell("Name");
                tbl.addCell("Price");
                tbl.addCell("Discount");
                tbl.addCell("Quantity");
                tbl.addCell("Total");
                tbl.addCell("ID book");

                for (int i = 0; i < tblBillList.getRowCount(); i++) {
                    String id = tblBillList.getValueAt(i, 0).toString();
                    String isbn = tblBillList.getValueAt(i, 1).toString();
                    String n = tblBillList.getValueAt(i, 2).toString();
                    String p = tblBillList.getValueAt(i, 3).toString();
                    String d = tblBillList.getValueAt(i, 4).toString();
                    String q = tblBillList.getValueAt(i, 5).toString();
                    String t = tblBillList.getValueAt(i, 6).toString();
                    String idbook = tblBillList.getValueAt(i, 7).toString();
                    
                    tbl.addCell(id);
                    tbl.addCell(isbn);
                    tbl.addCell(n);
                    tbl.addCell(p);
                    tbl.addCell(d);
                    tbl.addCell(q);
                    tbl.addCell(t);
                    tbl.addCell(idbook);
                }
                doc.add(tbl);
                Paragraph paragraph3 = new Paragraph("\nTotal: " + finalTotalText.getText() 
                        + "\nPaid Amount: " + paidAmountText.getText() 
                        + "\nReturn Amount: " + returnAmountText.getText()
                        + "\n--------------------------------Thank you for coming to shop. See you next time---------------------------------");
                doc.add(paragraph3);
                JOptionPane.showMessageDialog(null, "Bill has been printed");


            } catch (Exception e) {
                e.printStackTrace();
            }
            doc.close();
            
            
            idNewBill.setText("");
            textIdCustomer.setText("");
            billCustomerName.setText("");
            billCustomerPhone.setText("");
            // Date and Time
            dateNowBill();
            timeNowBill();
            textIdEmployee.setText("");
            employeeBillName.setText("");
            employeeBillPhone.setText("");
            textIdBook.setText("");
            bookTextISBN.setText("");
            bookTextTitle.setText("");
            textPriceBill.setText("");
            textQuantityBill.setText("");
            comboDiscountBill.setSelectedItem("0");
            DefaultTableModel dtm = (DefaultTableModel) tblBillList.getModel();
            dtm.setRowCount(0);
        }
        
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jPasswordField1.setText("");
        jPasswordField2.setText("");
        jPasswordField3.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void finalTotalTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalTotalTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finalTotalTextActionPerformed
      
    public void loadAccount() {
        AccountController ac = new AccountController();
        
        String usename = ac.readAccount();
        textUserName.setText(usename);
    }
    
    public void loadTableBillDate() {
        Date utilDate = checkDateSearch.getDate();
        LocalDate date = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        BillController bc = new BillController();
        List list = bc.loadDataDate(date);
        loadDataBill(list);
    }
    
    public boolean lg() {
        boolean check = true;
        
        
        
        return check;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeFrameaaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeFrameaaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeFrameaaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeFrameaaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeFrameaaa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBillBtn;
    private javax.swing.JButton addCategoryBtn;
    private javax.swing.JTextField billCustomerName;
    private javax.swing.JTextField billCustomerPhone;
    private javax.swing.JTextField bookTextISBN;
    private javax.swing.JTextField bookTextTitle;
    private javax.swing.JComboBox<String> boxGenderCustomer;
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnAddNewBill;
    private javax.swing.JButton btnClearCustomer;
    private javax.swing.JButton btnClearEmployee;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteEmployee;
    private javax.swing.JButton btnInsertCustomer;
    private javax.swing.JButton btnInsertEmployee;
    private javax.swing.JButton btnSearchBill;
    private javax.swing.JButton btnUpdateCustomer;
    private javax.swing.JButton btnUpdateEmployee;
    private javax.swing.JButton btnUpdatePass;
    private com.toedter.calendar.JDateChooser checkDateSearch;
    private javax.swing.JComboBox<String> comboDiscountBill;
    private javax.swing.JComboBox<String> comboGenderEmployee;
    private javax.swing.JComboBox<String> comboStatusEmployee;
    private javax.swing.JTextField dateTextBill;
    private javax.swing.JButton deleteBtnBill;
    private javax.swing.JButton deleteCategoryBtn;
    private javax.swing.JButton deleteRowBill;
    private javax.swing.JButton editCategoryBtn;
    private javax.swing.JTextField employeeBillName;
    private javax.swing.JTextField employeeBillPhone;
    private javax.swing.JTextField finalTotalText;
    private javax.swing.JTextField idNewBill;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField paidAmountText;
    private javax.swing.JButton refreshBtnBill;
    private javax.swing.JTextField returnAmountText;
    private javax.swing.JButton saveBillBtn;
    private javax.swing.JTable tblBill;
    private javax.swing.JTable tblBillBook;
    private javax.swing.JTable tblBillCustomer;
    private javax.swing.JTable tblBillEmployee;
    private javax.swing.JTable tblBillList;
    private javax.swing.JTable tblBook;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JTextArea textAddressCustomer;
    private javax.swing.JTextArea textAddressEmployee;
    private com.toedter.calendar.JDateChooser textDobCustomer;
    private com.toedter.calendar.JDateChooser textDobEmployee;
    private javax.swing.JLabel textIdBook;
    private javax.swing.JLabel textIdCustomer;
    private javax.swing.JLabel textIdEmployee;
    private javax.swing.JTextField textMailEmployee;
    private javax.swing.JTextField textNameCustomer;
    private javax.swing.JTextField textNameEmployee;
    private javax.swing.JTextField textPhoneCustomer;
    private javax.swing.JTextField textPhoneEmployee;
    private javax.swing.JTextField textPriceBill;
    private javax.swing.JTextField textQuantityBill;
    private javax.swing.JTextField textUserName;
    private javax.swing.JTextField timeTextBill;
    // End of variables declaration//GEN-END:variables
}
