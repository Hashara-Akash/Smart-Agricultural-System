package com.example.smartagriculturalsystem;

public class userHelper {
    String fname, username,email,telNum,password,cpassword;

    public userHelper() {
    }

    public userHelper(String fname, String username, String email, String telNum, String password, String cpassword) {
        this.fname = fname;
        this.username = username;
        this.email = email;
        this.telNum = telNum;
        this.password = password;
        this.cpassword = cpassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }
}
