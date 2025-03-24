
package com.model.employee;

import java.util.Date;


public class EmployeeModel {
    private Integer id;
    private String name;
    private Date dob;
    private String phone;
    private String mail;
    private String gender;
    private String status;
    private String address;

    public EmployeeModel(Integer id, String name, Date dob, String phone, String mail, String gender, String status, String address) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.mail = mail;
        this.gender = gender;
        this.status = status;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
}
