package com.example.zhaoy.weatherforecast.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 和服务器的交互
 * Created by Administrator on 2016/12/9.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
