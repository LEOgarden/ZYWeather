package com.example.zhaoy.weatherforecast.model.modelImp;

import android.os.AsyncTask;

import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.callback.WeatherCallback;
import com.example.zhaoy.weatherforecast.model.IWeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WeatherModel implements IWeatherModel {
    /**
     * 处理数据并回传
     * @param weatherId
     */
    @Override
    public void loadWeatherInfo(final String weatherId ,final WeatherCallback callback) {
        final String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+ weatherId +
                "&&key=b1f6ab435e824b859395e18cad58846d";
        AsyncTask<String ,String ,String> task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(weatherUrl);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream ,"utf-8")
                    );
                    String line = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuffer.append(line);
                    }
                    String json = stringBuffer.toString();
                    JSONObject object = new JSONObject(json);
                    String status = object.getString("status");
                    if ("ok".equals(status)){
                        JSONObject suggestion = object.getJSONObject("suggestion");
                        JSONArray dailyForecast = object.getJSONArray("daily_forecast");
                        JSONObject now = object.getJSONObject("now");
                        JSONObject basic = object.getJSONObject("basic");
                        JSONObject aQI = object.getJSONObject("aqi");
                        JSONObject cityObj = aQI.getJSONObject("city");
                        String aqi = cityObj.getString("aqi");//aqi指数
                        String pm25 = cityObj.getString("pm25");//pm2.5指数
                        String city = basic.getString("city");//城市名
                        JSONObject updateObj = basic.getJSONObject("update");
                        String update = updateObj.getString("loc");//更新时间
                        String tmp = now.getString("tmp");//温度
                        JSONObject condObj = now.getJSONObject("cond");
                        String currentStatus = condObj.getString("txt");//当前天气状况
                        JSONObject comfObj = suggestion.getJSONObject("comf");
                        String comfort = comfObj.getString("txt");//舒适度
                        JSONObject cwObj = suggestion.getJSONObject("cw");
                        String carWash = cwObj.getString("txt");//洗车建议
                        JSONObject sportObj = suggestion.getJSONObject("sport");
                        String sportSuggestion = sportObj.getString("txt");//运动建议
                        Weather weather = new Weather();
                        weather.setaQI(aqi);
                        weather.setpM(pm25);
                        weather.setCarWashing(carWash);
                        weather.setComfort(comfort);
                        weather.setDate(update);
                        weather.setSportSug(sportSuggestion);
                        weather.setStatus(currentStatus);
                        weather.setTmp(tmp);
                        weather.setCurrentCity(city);

                        //回调
                        callback.onSuccess(weather);
                    }else {
                        callback.onError();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
