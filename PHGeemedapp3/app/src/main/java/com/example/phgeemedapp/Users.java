package com.example.phgeemedapp;

public class Users {

        String name,email, phonenum, username, password;

    public Users() {
    }

    public Users(String name, String email, /*String username,*/ String phonenum, String password) {
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        //this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

   /* public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
