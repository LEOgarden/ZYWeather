package com.example.zhaoy.weatherforecast.model.modelImp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zhaoy.weatherforecast.MyApplication;
import com.example.zhaoy.weatherforecast.bean.Weather;
import com.example.zhaoy.weatherforecast.callback.WeatherCallback;
import com.example.zhaoy.weatherforecast.model.IWeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Administrator on 2016/12/14.
 */

public class WeatherModel implements IWeatherModel {

    private RequestQueue queue;

    public WeatherModel(){
        queue = Volley.newRequestQueue(MyApplication.getContext());
    }
    /**
     * 处理数据并回传
     * @param weatherId
     */
    @Override
    public void loadWeatherInfo(String weatherId ,final WeatherCallback callback) {
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+ weatherId +
                "&&key=b1f6ab435e824b859395e18cad58846d";
        StringRequest request = new StringRequest(weatherUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray heWeather = object.getJSONArray("HeWeather5");
                    JSONObject wObj = heWeather.getJSONObject(0);
                    String status = wObj.getString("status");
                    if ("ok".equals(status)) {
                        JSONObject suggestion = wObj.getJSONObject("suggestion");
                        JSONArray dailyForecast = wObj.getJSONArray("daily_forecast");
                        JSONObject now = wObj.getJSONObject("now");
                        JSONObject basic = wObj.getJSONObject("basic");
                        JSONObject aQI = wObj.getJSONObject("aqi");
                        JSONObject cityObj = aQI.getJSONObject("city");
                        Log.v("aqi",aQI.toString());
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
                    } else {
                        callback.onError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
}
