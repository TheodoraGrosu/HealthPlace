package com.example.healthplace.Database.model;

public class OnlineAppointment {
    private int idAppointment;
    private int idPatient;
    private int idDoctor;
    private String service;
    private String date;
    private String time;
    private String department;
    private Boolean is_confirmed;

    public Boolean getIs_confirmed() {
        return is_confirmed;
    }

    public void setIs_confirmed(Boolean is_confirmed) {
        this.is_confirmed = is_confirmed;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "OnlineAppointment{" +
                "idAppointment=" + idAppointment +
                ", idPatient=" + idPatient +
                ", idDoctor=" + idDoctor +
                ", service='" + service + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", department='" + department + '\'' +
                ", is_confirmed='" + is_confirmed + '\'' +
                '}';
    }
}
