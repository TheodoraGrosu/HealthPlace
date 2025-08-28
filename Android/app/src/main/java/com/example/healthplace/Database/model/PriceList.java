package com.example.healthplace.Database.model;

public class PriceList {
    private int id;
    private String service;
    private String department;
    private float price;
    private int discountMedicalRaport;
    private int discountLoyaltyCard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscountMedicalRaport() {
        return discountMedicalRaport;
    }

    public void setDiscountMedicalRaport(int discountMedicalRaport) {
        this.discountMedicalRaport = discountMedicalRaport;
    }

    public int getDiscountLoyaltyCard() {
        return discountLoyaltyCard;
    }

    public void setDiscountLoyaltyCard(int discountLoyaltyCard) {
        this.discountLoyaltyCard = discountLoyaltyCard;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                ", service='" + service + '\'' +
                ", department='" + department + '\'' +
                ", price=" + price +
                ", discountMedicalRaport=" + discountMedicalRaport +
                ", discountLoyaltyCard=" + discountLoyaltyCard +
                '}';
    }
}
