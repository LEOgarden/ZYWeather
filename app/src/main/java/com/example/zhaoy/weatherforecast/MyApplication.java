package com.example.zhaoy.weatherforecast;

import android.app.Activity;
import android.os.Process;
import android.util.Log;

import org.litepal.LitePalApplication;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MyApplication extends LitePalApplication{
    private static MyApplication context;
    public ArrayList<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this ;
    }

    public static MyApplication getContext(){
        return context;
    }

    public void finishActivity(){
        for (Activity activity : activityList){
            try{
                activity.finish();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //结束进程
        Process.killProcess(Process.myPid());
    }
}
