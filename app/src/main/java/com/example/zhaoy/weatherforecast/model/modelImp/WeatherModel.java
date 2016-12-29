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
        Log.v("loadWeathermodel","执行");
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city="+ weatherId +
                "&&key=b1f6ab435e824b859395e18cad58846d";
        StringRequest request = new StringRequest(weatherUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("parseJson",response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray heWeather = object.getJSONArray("HeWeather5");
                    JSONObject wObj = heWeather.getJSONObject(0);
                    String status = wObj.getString("status");
                    Log.v("ok","ok".equals(status)+"");
                    Weather weather = new Weather();
                    if ("ok".equals(status)) {
                        Log.v("xxxxx","zzzzzzzzzzz");
                        JSONObject suggestion = wObj.getJSONObject("suggestion");
                        JSONArray dailyForecast = wObj.getJSONArray("daily_forecast");
                        JSONObject now = wObj.getJSONObject("now");
                        JSONObject basic = wObj.getJSONObject("basic");
                        Log.v("mmmmmm",wObj.has("aqi")+"*************");
                        if(wObj.has("aqi")){
                            JSONObject aQI = wObj.getJSONObject("aqi");
                            JSONObject cityObj = aQI.getJSONObject("city");
                            String aqi = cityObj.getString("aqi");//aqi指数
                            String pm25 = cityObj.getString("pm25");//pm2.5指数
                            weather.setaQI(aqi);
                            weather.setpM(pm25);
                        }else{
                            weather.setaQI("未获取到数据");
                            weather.setpM("未获取到数据");
                        }
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
                        weather.setCarWashing(carWash);
                        weather.setComfort(comfort);
                        weather.setDate(update);
                        weather.setSportSug(sportSuggestion);
                        weather.setStatus(currentStatus);
                        weather.setTmp(tmp);
                        weather.setCurrentCity(city);
                        Log.v("weather",object.toString());
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
