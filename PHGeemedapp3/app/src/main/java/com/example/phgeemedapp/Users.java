package com.example.phgeemedapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Users  implements Serializable {

    public static class Information implements Serializable {
        private String bloodType, dob, martialStatus, address, nameE, emailE, addressE, phoneE, prevMed, currIll, specficAllergies;
        private int age;

        public Information() {
            this.bloodType = "null";
            this.dob = "null";
            this.martialStatus = "null";
            this.address = "null";
            this.nameE = "null";
            this.emailE = "null";
            this.addressE = "null";
            this.phoneE = "null";
            this.prevMed = "null";
            this.currIll = "null";
            this.specficAllergies = "null";
            this.age = 0;
        }

        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMartialStatus() {
            return martialStatus;
        }

        public void setMartialStatus(String martialStatus) {
            this.martialStatus = martialStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNameE() {
            return nameE;
        }

        public void setNameE(String nameE) {
            this.nameE = nameE;
        }

        public String getEmailE() {
            return emailE;
        }

        public void setEmailE(String emailE) {
            this.emailE = emailE;
        }

        public String getAddressE() {
            return addressE;
        }

        public void setAddressE(String addressE) {
            this.addressE = addressE;
        }

        public String getPhoneE() {
            return phoneE;
        }

        public void setPhoneE(String phoneE) {
            this.phoneE = phoneE;
        }

        public String getPrevMed() {
            return prevMed;
        }

        public void setPrevMed(String prevMed) {
            this.prevMed = prevMed;
        }

        public String getCurrIll() {
            return currIll;
        }

        public void setCurrIll(String currIll) {
            this.currIll = currIll;
        }

        public String getSpecficAllergies() {
            return specficAllergies;
        }

        public void setSpecficAllergies(String specficAllergies) {
            this.specficAllergies = specficAllergies;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    Information userInformation;

    String name,email, phonenum, password,role, userID, profileURl,refuser;
    String totalAppointments;
    List<appointment> appointments;
    appointment appointment;

    //BasicInfo basicInfo;

    List<Users> patientsList; // List of patients for each doctor

    public Users() {
        this.patientsList = new ArrayList<>();
        this.userInformation = new Information();

    }

    public Users(String name, String email, String phonenum, String password, String Role){
        //this.userID = userID;
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.password = password;
        this.role = Role;
        this.patientsList = new ArrayList<>();
        this.userInformation = new Information();
        this.appointments = new ArrayList<>();
        this.refuser = "null";


    }

    public String getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(String totalAppointments) {
        this.totalAppointments = totalAppointments;
    }
    public int getSize(List<appointment> appointments){
        return appointments.size();
    }

    public List<appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<appointment> appointments) {
        this.appointments = appointments;
    }
    public void addAppointments(appointment appointment){
        appointments.add(appointment);
    }
    public void setAppointment(appointment appointment){
        this.appointment = appointment;
    }
    public String getRef() {
        return refuser;
    }

    public void setRef(String refuser) {
        this.refuser = refuser;
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

    public int getPosition(Users user){
        if(user!=null) {
            for (int i = 0; i < patientsList.size(); i++) {
                if (patientsList.get(i).getName().equals(user.getName())) {
                    return i;
                }
            }
        }
        return -1;
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
    public String getProfilePic(){
        return profileURl;
    }
    public void setProfilePic(String url){
        this.profileURl = url;
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