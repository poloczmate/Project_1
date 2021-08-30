package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v) throws InterruptedException {
        TextView we = (TextView) findViewById(R.id.weather);
        TextView te = (TextView) findViewById(R.id.temp);
        TextView wi = (TextView) findViewById(R.id.wind);
        TextView tv = (TextView) findViewById(R.id.cityName);
        String city = tv.getText().toString();

        CurrentWeather CW = new CurrentWeather();
        we.setText(CW.getWeather(city));
        te.setText("Celsius: " + CW.getCelsius(city) + ", feels like: " + CW.getFeelsLike(city) + " celsius");
        wi.setText(CW.getWind(city));
    }
}