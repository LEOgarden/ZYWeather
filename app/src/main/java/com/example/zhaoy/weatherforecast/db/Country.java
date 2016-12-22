package com.example.zhaoy.weatherforecast.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/12/9.
 */

public class Country extends DataSupport{
    private int id;
    private String countryName;
    private int countryCode;
    private String weatherId;
    private int cityId;

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
}
