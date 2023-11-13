package com.example.combine.DataClass;

public class OilData {
    private String name;
    private String price;

    private String unit;

    private String state;
    private String value;

    public OilData(String name, String price, String unit , String state,String value) {
        this.name = name;
        this.price = price;
        this.unit=unit;
        this.state=state;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit(){return unit;}

    public String getState(){return state;}
    public String getValue() {
        return value;
    }
}
