package com.example.phgeemedapp;

public class patient {
    String patientID, firstName, middleName, lastName,pemail, dateBirth, bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies;

    public patient(String patientID, String firstName, String middleName, String lastName, String pemail, String dateBirth, String bloodType, String RHfactor, String maritalStatus, String age,
                   String phone, String mobile, String ememail, String emName, String emPhone, String currentIllnesses, String previousIllnesses, String allergies) {
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

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getpEmail() {
        return pemail;
    }

    public void setpEmail(String pemail) {
        this.pemail = pemail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRHfactor() {
        return RHfactor;
    }

    public void setRHfactor(String RHfactor) {
        this.RHfactor = RHfactor;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getemEmail() {
        return ememail;
    }

    public void setemEmail(String email) {
        this.ememail = email;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emcName) {
        this.emName = emcName;
    }

    public String getEmPhone() {
        return emPhone;
    }

    public void setEmPhone(String emvPhone) {
        this.emPhone = emvPhone;
    }

    public String getCurrentIllnesses() {
        return currentIllnesses;
    }

    public void setCurrentIllnesses(String currentIllnesses) {
        this.currentIllnesses = currentIllnesses;
    }

    public String getPreviousIllnesses() {
        return previousIllnesses;
    }

    public void setPreviousIllnesses(String previousIllnesses) {
        this.previousIllnesses = previousIllnesses;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}