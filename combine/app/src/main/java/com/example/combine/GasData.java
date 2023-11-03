package com.example.combine;

public class GasData {
    private String name;
    private String price;

    private String unit;
    private String value;

    public GasData(String name, String price,String unit ,String value) {
        this.name = name;
        this.price = price;
        this.unit=unit;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit(){return unit;}
    public String getValue() {
        return value;
    }
}
