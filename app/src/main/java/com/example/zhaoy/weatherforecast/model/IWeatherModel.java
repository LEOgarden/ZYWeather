package com.example.zhaoy.weatherforecast.model;

import com.example.zhaoy.weatherforecast.callback.WeatherCallback;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface IWeatherModel {
    void loadWeatherInfo(String weatherId ,WeatherCallback callback);
}
