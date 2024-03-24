package com.example.phgeemedapp;

public class Users {

    String name,email, phonenum, password,role;

    //BasicInfo basicInfo;

    public Users() {
    }

    public Users(String name, String email,  String phonenum, String password) {
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        //this.username = username;
        this.password = password;
    }
    public String getRole(){return role;}
    public void setRole(String role){this.role = role;}

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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* public class BasicInfo {

        public String bloodType = "Need Update";
        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }
   }
    */
}