package kofidankwa.com.myweatherapp.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;

import kofidankwa.com.myweatherapp.R;
import kofidankwa.com.myweatherapp.models.HourlyWeather;
import kofidankwa.com.myweatherapp.models.WeeklyWeather;

public class HourlyWeatherReport extends Activity {

    public HashMap<String, Object> mData = Data();
    public HourlyWeather[] mHourlyWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHourlyWeather = (HourlyWeather[]) mData.get("Hour");
    }

    public void sendMessage(View view){
        Intent i = new Intent(this, WeeklyWeatherReport.class);
        i.putExtra("data", Data());
        startActivity(i);
    }

    public HashMap<String, Object> Data() {
      Intent i = getIntent();
        return i.getParcelableExtra("data");
    }
}
