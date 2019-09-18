package com.bamplee.chomi.api.application;

public class WeatherResponse {
    private String county;
    private String village;
    private String city;
    private String skyName;
    private Integer tc;
    private Integer tmax;
    private Integer tmin;
    private Double humidity;
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

    public Integer getTc() {
        return tc;
    }

    public void setTc(Integer tc) {
        this.tc = tc;
    }

    public Integer getTmax() {
        return tmax;
    }

    public void setTmax(Integer tmax) {
        this.tmax = tmax;
    }

    public Integer getTmin() {
        return tmin;
    }

    public void setTmin(Integer tmin) {
        this.tmin = tmin;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
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
