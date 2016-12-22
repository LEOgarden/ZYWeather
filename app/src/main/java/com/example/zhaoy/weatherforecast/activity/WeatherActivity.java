package com.example.zhaoy.weatherforecast.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zhaoy.weatherforecast.R;
import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.util.Utility;
import com.example.zhaoy.weatherforecast.view.IShowWeather;

public class WeatherActivity extends AppCompatActivity implements IShowWeather{
    //滚动视图
    private ScrollView weatherLayout;
    //城市名
    private TextView titleCity;
    //更新时间
    private TextView titleUpdate;
    //温度
    private TextView degreeText;
    //天气状况
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    //aqi
    private TextView aqiText;
    //pm2.5
    private TextView pmText;
    //舒适度
    private TextView comfortText;
    //洗车
    private TextView carWashtext;
    //运动建议
    private TextView sportText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();//初始化控件
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if (weatherString != null){
            //有缓存时直接解析天气数据

        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdate = (TextView) findViewById(R.id.title_updateTime);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.foecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pmText = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashtext = (TextView) findViewById(R.id.comfort_text);
        sportText = (TextView) findViewById(R.id.sport_text);
    }

    @Override
    public void showWeatherView(Weather weather) {
        titleCity.setText(weather.getCurrentCity());
        titleUpdate.setText(weather.getDate());
        degreeText.setText(weather.getTmp()+"℃");
        weatherInfoText.setText(weather.getStatus());
        aqiText.setText(weather.getaQI());
        pmText.setText(weather.getpM());
        comfortText.setText(""+weather.getComfort());
        carWashtext.setText(""+weather.getCarWashing());
        sportText.setText(weather.getSportSug());
    }
}
