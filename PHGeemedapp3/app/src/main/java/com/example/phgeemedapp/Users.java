package com.example.phgeemedapp;

public class Users {

    String name,email, phonenum, password,role,patientID, firstName, middleName, lastName,pemail, dateBirth,
            bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies;

    //BasicInfo basicInfo;

    public Users() {
    }

    public Users(String name, String email, String phonenum, String password, String patientID, String firstName, String middleName, String lastName, String pemail, String dateBirth, String bloodType, String RHfactor, String maritalStatus, String age, String phone, String mobile, String ememail,
                 String emName, String emPhone, String currentIllnesses, String previousIllnesses, String allergies) {
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.password = password;
        this.role = role;
        this.patientID = patientID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.pemail = pemail;
        this.dateBirth = dateBirth;
        this.bloodType = bloodType;
        this.RHfactor = RHfactor;
        this.maritalStatus = maritalStatus;
        this.age = age;
        this.phone = phone;
        this.mobile = mobile;
        this.ememail = ememail;
        this.emName = emName;
        this.emPhone = emPhone;
        this.currentIllnesses = currentIllnesses;
        this.previousIllnesses = previousIllnesses;
        this.allergies = allergies;
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