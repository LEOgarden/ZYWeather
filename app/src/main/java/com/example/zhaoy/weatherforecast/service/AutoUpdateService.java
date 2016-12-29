package com.example.zhaoy.weatherforecast.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.util.HttpUtil;
import com.example.zhaoy.weatherforecast.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 后台更新天气
 * Created by Administrator on 2016/12/28.
 */

public class AutoUpdateService extends Service {
    public String weatherId;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        weatherId = intent.getStringExtra("weather_id");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();//gengxin天气
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int EightHours = 8*60*60*1000;//8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime()+EightHours;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

    /**
     * 更新天气信息
     */
    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if (weatherString != null){
            //有缓存直接解析
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+ weatherId +
                    "&&key=b1f6ab435e824b859395e18cad58846d";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather = Utility.handleWeatherResponse(responseText);
                    if (weather != null ){
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",responseText);
                        editor.apply();
                    }
                }
            });
        }
    }
}
