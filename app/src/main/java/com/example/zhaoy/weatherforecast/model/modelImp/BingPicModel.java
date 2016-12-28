package com.example.zhaoy.weatherforecast.model.modelImp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.zhaoy.weatherforecast.activity.WeatherActivity;
import com.example.zhaoy.weatherforecast.callback.PicCallback;
import com.example.zhaoy.weatherforecast.model.IBingPicModel;
import com.example.zhaoy.weatherforecast.presenter.presenterImpl.PicturePresenterImpl;
import com.example.zhaoy.weatherforecast.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/27.
 */

public class BingPicModel implements IBingPicModel{
    /**
     * 加载图片
     */
    @Override
    public void loadBingPic(final PicCallback callback) {
        Log.v("loadPic","执行");
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("response",response.toString());
                String bingPic = response.body().string();
                callback.onSuccess(bingPic);
            }
        });
    }
}
