package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v){
        TextView tv = (TextView) findViewById(R.id.alma);
        CurrentWeatherAPI CWAPI = new CurrentWeatherAPI();
        tv.setText(CWAPI.getNews());
    }
}