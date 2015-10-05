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

public class WeeklyWeatherReport extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void sendMessage(View view) {
// do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("data", Data());
        startActivity(intent);
    }

    public HashMap<String, Object> Data() {
        Intent i = getIntent();
        return i.getParcelableExtra("data");
    }

}