package com.example.zhaoy.weatherforecast.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zhaoy.weatherforecast.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, WeatherActivity.class);
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather",null) != null){
            Log.d("xxxxxxx",prefs.getString("weather",null));
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }*/
    }
}
