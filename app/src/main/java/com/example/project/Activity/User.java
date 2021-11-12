package com.example.project.Activity;

public class User {
    String email, full, phone;

    public User() {
    }

    public User(String email, String full, String phone) {
        this.email = email;
        this.full = full;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}