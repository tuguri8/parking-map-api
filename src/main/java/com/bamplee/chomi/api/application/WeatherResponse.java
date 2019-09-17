package com.bamplee.chomi.api.application;

public class WeatherResponse {
    private String county;
    private String village;
    private String city;
    private String skyName;
    private String tc;
    private String tmax;
    private String tmin;
    private String humidity;
    private Integer airValue;
    private String airGrade;
    private String itemCode;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkyName() {
        return skyName;
    }

    public void setSkyName(String skyName) {
        this.skyName = skyName;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }

    public String getTmin() {
        return tmin;
    }

    public void setTmin(String tmin) {
        this.tmin = tmin;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public Integer getAirValue() {
        return airValue;
    }

    public void setAirValue(Integer airValue) {
        this.airValue = airValue;
    }

    public String getAirGrade() {
        return airGrade;
    }

    public void setAirGrade(String airGrade) {
        this.airGrade = airGrade;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}
