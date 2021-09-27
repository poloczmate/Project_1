package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CityWeatherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
    }

    public void getWeather(View view){
        TextView tv = (TextView) findViewById(R.id.cityInput);
        String city = tv.getText().toString();

        OneCallWeather OCW = new OneCallWeather(city);
        //wait for the API response
        try {
            while (OCW.response.equals("")) {
                OCW.getResponse();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get GUI elements
        TextView we = (TextView) findViewById(R.id.weather2);
        TextView te = (TextView) findViewById(R.id.temp2);
        TextView wi = (TextView) findViewById(R.id.wind2);
        ImageView iv = (ImageView) findViewById(R.id.imageCity);

        //update GUI
        we.setText(OCW.getWeather());
        te.setText("Celsius: " + OCW.getCelsius() + ", feels like: " + OCW.getFeelsLike() + " celsius");
        wi.setText(OCW.getWind());
        iv.setImageResource(OCW.getWeatherID());
    }
}