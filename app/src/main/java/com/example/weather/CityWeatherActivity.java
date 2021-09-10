package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CityWeatherActivity extends AppCompatActivity {
    double lat = 0;
    double lon = 0;
    LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
    }

    public void getWeather(View view){
        TextView tv = (TextView) findViewById(R.id.cityInput);
        String city = tv.getText().toString();
        locationTracker = new LocationTracker(this);
        if (locationTracker.canGetLocation){
            locationTracker.getLocation();
            this.lat = locationTracker.getLatitude();
            this.lon = locationTracker.getLongitude();
        }

        //wait for the response from locationtracker
        try {
            while (this.lat == 0 && this.lon == 0) {
                TimeUnit.MILLISECONDS.sleep(500);
                this.lat = locationTracker.getLatitude();
                this.lon = locationTracker.getLongitude();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OneCallWeather OCW = new OneCallWeather(lat,lon);
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

        //update GUI
        we.setText(OCW.getWeather());
        te.setText("Celsius: " + OCW.getCelsius() + ", feels like: " + OCW.getFeelsLike() + " celsius");
        wi.setText(OCW.getWind());
    }
}