package com.example.combine;

public class ForecastData {

    private String date;
    private String tem;
    private String rain;
    private String feel;
    private String humid;
    public ForecastData(String date, String tem, String rain , String feel, String humid) {
        this.date=date;
        this.tem=tem;
        this.rain=rain;
        this.feel=feel;
        this.humid=humid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }
    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain =rain ;
    }
    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }
    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }


}