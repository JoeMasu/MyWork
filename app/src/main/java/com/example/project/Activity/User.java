package com.example.project.Activity;

public class User {
   public String full;
   public String phone;
   public String email;

    public User() {
    }

    public User(String full, String phone, String email) {
        this.full = full;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
