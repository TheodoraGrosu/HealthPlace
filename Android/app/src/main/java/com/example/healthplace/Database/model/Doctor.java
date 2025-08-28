package com.example.healthplace.Database.model;

import java.io.Serializable;

public class Doctor  implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String speciality;
    private String department;
    private float salary;
    private String email;
    private String employmentDate;
    private String workProgram;
    private String daysWorking;
    private String password;


    public Doctor() {
        this.lastName = "-";
        this.firstName = "-";
        this.address = "-";
        this.speciality = "-";
        this.department = "-";
        this.salary = 0;
        this.email = "-";
        this.employmentDate = "-";
        this.workProgram = "-";
        this.daysWorking = "-";
        this.password = "-";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDaysWorking() {
        return daysWorking;
    }

    public void setDaysWorking(String daysWorking) {
        this.daysWorking = daysWorking;
    }

    public String getWorkProgram() {
        return workProgram;
    }

    public void setWorkProgram(String workProgram) {
        this.workProgram = workProgram;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }
}
