package com.example.healthplace.SpringServer.model.PriceModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
