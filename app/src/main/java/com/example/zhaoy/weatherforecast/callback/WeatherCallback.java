package com.example.zhaoy.weatherforecast.callback;

import java.util.Objects;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface WeatherCallback {
    void onSuccess(Object success);
    void onError();
}
