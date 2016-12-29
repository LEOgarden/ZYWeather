package com.example.zhaoy.weatherforecast.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhaoy.weatherforecast.R;
import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.presenter.IPicturePresenter;
import com.example.zhaoy.weatherforecast.presenter.IWeatherPresenter;
import com.example.zhaoy.weatherforecast.presenter.presenterImpl.PicturePresenterImpl;
import com.example.zhaoy.weatherforecast.presenter.presenterImpl.WeatherPresenterImp;
import com.example.zhaoy.weatherforecast.service.AutoUpdateService;
import com.example.zhaoy.weatherforecast.util.Utility;
import com.example.zhaoy.weatherforecast.view.IShowPicture;
import com.example.zhaoy.weatherforecast.view.IShowWeather;

public class WeatherActivity extends AppCompatActivity implements IShowWeather ,IShowPicture{
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
    //背景图
    private ImageView bingPicImg;
    //手动gengxin
    public SwipeRefreshLayout swipeRefresh;
    private Button navButton;
    public DrawerLayout drawerLayout;
    private String weatherId;

    public IWeatherPresenter presenter;
    private Weather weather;

    private IPicturePresenter picPresenter;


    //更新天气handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Weather weather = (Weather) msg.obj;
                    titleCity.setText(weather.getCurrentCity());
                    titleUpdate.setText(weather.getDate());
                    degreeText.setText(weather.getTmp() + "℃");
                    weatherInfoText.setText(weather.getStatus());
                    aqiText.setText(weather.getaQI());
                    pmText.setText(weather.getpM());
                    comfortText.setText("生活指南：" + weather.getComfort());
                    carWashtext.setText("洗车建议：" + weather.getCarWashing());
                    sportText.setText("运动建议：" + weather.getSportSug());
                    //缓存到SharedPreferences中
                    //1.实例化Sharedpreferences对象
                    SharedPreferences mySharedPreferences = getSharedPreferences("weather", Activity.MODE_PRIVATE);
                    //2.实例化sharedPreferences.Editor对象
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    //3.保存数据
                    editor.putString("weather",weather.toString());
                    //4.提交当前数据
                    editor.commit();
                    break;
            }
        }
    };
    //开启处理图片的操作
    private Handler picHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    final String bingPic = (String) msg.obj;
                    SharedPreferences.Editor editor = PreferenceManager.
                            getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putString("bing_pic", bingPic);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getApplicationContext()).load(bingPic).into(bingPicImg);
                        }
                    });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        initView();//初始化控件
//        picPresenter = new PicturePresenterImpl(this);
//        picPresenter.loadPicture();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        Log.v("weatherString",weatherString+"");
        if (weatherString != null){
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherView(weather);
        }else{
            weatherId = getIntent().getStringExtra("weather_id");
            Log.v("weatherId",weatherId);
            presenter.loadWeatherInfo(weatherId);
        }
        setListener();//设置监听
        //把weatherId传给service
        Intent idIntent = new Intent(this, AutoUpdateService.class);
        idIntent.putExtra("weather_id",weatherId);
        startService(idIntent);
    }

    private void setListener() {
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        navButton = (Button) findViewById(R.id.nav_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        presenter = new WeatherPresenterImp(this);
    }


    @Override
    public void showWeatherView(final Weather weather) {
        this.weather = weather;
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = weather;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 设置背景图片
     * @param bingPic
     */
    @Override
    public void showPic(final String bingPic) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message = new Message();
                message.obj = bingPic;
                message.what = 2;
                picHandler.sendMessage(message);
            }
        };
    }
}
