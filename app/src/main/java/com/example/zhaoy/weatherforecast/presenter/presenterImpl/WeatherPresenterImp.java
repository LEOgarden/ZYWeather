package com.example.zhaoy.weatherforecast.presenter.presenterImpl;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.callback.WeatherCallback;
import com.example.zhaoy.weatherforecast.model.IWeatherModel;
import com.example.zhaoy.weatherforecast.model.modelImp.WeatherModel;
import com.example.zhaoy.weatherforecast.presenter.IWeatherPresenter;
import com.example.zhaoy.weatherforecast.view.IShowWeather;

import java.util.Objects;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WeatherPresenterImp implements IWeatherPresenter {
    //用于加载
    private IWeatherModel weatherModel;
    //用于显示
    private IShowWeather showWeather;

    public WeatherPresenterImp(IShowWeather showWeather) {
        this.weatherModel = new WeatherModel();
        this.showWeather = showWeather;
    }

    @Override
    public void loadWeatherInfo(String weatherId) {
        Log.v("loadWeatherInfo","zhixign");
        weatherModel.loadWeatherInfo(weatherId , new WeatherCallback() {
            @Override
            public void onSuccess(Object success) {
                Weather weather = (Weather) success;
                showWeather.showWeatherView(weather);
            }

            @Override
            public void onError() {
               Log.v("ERROR","没有获取到数据");
            }
        });

    }
}
