package com.jet.peoplemanagement.shipment;

import lombok.Data;

@Data
public class Product {

    private String name;
    private int quantity;
    private String id;
    private double price;

    public Product(){

    }
}
