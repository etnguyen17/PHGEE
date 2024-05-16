package com.example.phgeemedapp;

public class appointment {
    String date, time, patientName,  totalappointments;
    public appointment() {

    }
    public appointment(String patientName, String date, String time){
        this.patientName = patientName;
        this.date = date;
        this.time = time;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getpatientName() {
        return patientName;
    }

    public void setpatientName(String patientName) {
        this.patientName = patientName;
    }
    public String toString(){
        String s = "";
        s = s + "Name: " + getpatientName();
        s = s + "\nDate:"  + getDate();
        s = s + "\nTime: " + getTime();
        return s;
    }
}
