package com.example.zhaoy.weatherforecast.bean;

/**
 * Created by Administrator on 2016/12/14.
 */

public class Weather {
    //日期
    private String date;
    //状况
    private String status;
    //温度
    private String tmp;
    //pm2.5指数
    private String pM;
    //空气质量
    private String aQI;
    //舒适度
    private String comfort;
    //洗车指数
    private String carWashing;
    //运动建议
    private String sportSug;
    //当前城市
    private String currentCity;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getpM() {
        return pM;
    }

    public void setpM(String pM) {
        this.pM = pM;
    }

    public String getaQI() {
        return aQI;
    }

    public void setaQI(String aQI) {
        this.aQI = aQI;
    }

    public String getComfort() {
        return comfort;
    }

    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    public String getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(String carWashing) {
        this.carWashing = carWashing;
    }

    public String getSportSug() {
        return sportSug;
    }

    public void setSportSug(String sportSug) {
        this.sportSug = sportSug;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", tmp='" + tmp + '\'' +
                ", pM='" + pM + '\'' +
                ", aQI='" + aQI + '\'' +
                ", comfort='" + comfort + '\'' +
                ", carWashing='" + carWashing + '\'' +
                ", sportSug='" + sportSug + '\'' +
                ", currentCity='" + currentCity + '\'' +
                '}';
    }
}
