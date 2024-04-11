package com.example.phgeemedapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Users {

    String name,email, phonenum, password,role, userID;

    //BasicInfo basicInfo;

    List<Users> patientsList; // List of patients for each doctor

    public Users() {
        this.patientsList = new ArrayList<>();

    }

    public Users(String name, String email, String phonenum, String password, String Role) {
        //this.userID = userID;
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.password = password;
        this.role = Role;
        this.patientsList = new ArrayList<>();

    }

    public List<Users> getPatients() {
        return patientsList;
    }

    public void setPatients(List<Users> patients) {
        this.patientsList = patients;
    }

    public void addPatient(Users patient) {
        patientsList.add(patient);
    }

    public void removePatient(Users patient) {
        // Remove the patient from the local list
        patientsList.remove(patient);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getIDUser() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID= userID;
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

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}