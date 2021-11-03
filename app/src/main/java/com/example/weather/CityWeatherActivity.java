package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class CityWeatherActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    OneCallWeather OCW = null;
    Boolean switchValue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
    }

    public void getWeather(View view){
        TextView tv = (TextView) findViewById(R.id.cityInput);
        String city = tv.getText().toString();
        Switch sw = (Switch) findViewById(R.id.switch3);
        sw.setOnCheckedChangeListener(this);

        OCW = new OneCallWeather(city, switchValue);
        //wait for the API response
        try {
            while (OCW.response.equals("")) {
                OCW.getResponse();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateGUI();
    }

    public void updateGUI(){
        //get GUI elements
        TextView we = (TextView) findViewById(R.id.weather2);
        TextView te = (TextView) findViewById(R.id.temp2);
        TextView wi = (TextView) findViewById(R.id.wind2);
        ImageView iv = (ImageView) findViewById(R.id.imageCity);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout2);

        //update GUI
        we.setText(OCW.getWeather());
        te.setText(OCW.getTemperatures() + ", feels like: " + OCW.getFeelsLike());
        wi.setText(OCW.getWind());
        iv.setImageResource(OCW.getWeatherID());
        cl.setBackgroundColor(OCW.getColor());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Temperatures switched to " + (isChecked ? "Fahrenheit" : "Celsius"),
                Toast.LENGTH_SHORT).show();
        OCW.changeTemp(isChecked);
        switchValue = isChecked;
        updateGUI();
    }
}