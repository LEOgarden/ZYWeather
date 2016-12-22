package com.example.zhaoy.weatherforecast.util;

import android.text.TextUtils;

import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.db.City;
import com.example.zhaoy.weatherforecast.db.Country;
import com.example.zhaoy.weatherforecast.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 处理解析json
 * Created by Administrator on 2016/12/9.
 */

public class Utility {
    /**
     * 解析省份数据
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray AllProvince = new JSONArray(response);
                for (int i=0; i<AllProvince.length(); i++){
                    JSONObject provinceObj = AllProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(provinceObj.getInt("id"));
                    province.setProvinceName(provinceObj.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *解析城市名数据
     */
    public static boolean handleCityResponse(String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray cities = new JSONArray(response);
                for (int i=0; i<cities.length(); i++){
                    JSONObject cityObj = cities.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObj.getInt("id"));
                    city.setCityName(cityObj.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析县市数据
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountryResponse(String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray countries = new JSONArray(response);
                for (int i=0; i<countries.length(); i++){
                    JSONObject countryObj = countries.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryCode(countryObj.getInt("id"));
                    country.setCountryName(countryObj.getString("name"));
                    country.setCityId(cityId);
                    country.setWeatherId(countryObj.getString("weather_id"));
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject object = new JSONObject(response);
            Weather weather = new Weather();
            weather.setCurrentCity(object.getString(""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
