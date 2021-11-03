package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    double lat = 0;
    double lon = 0;
    LocationTracker locationTracker;
    boolean permissionsGranted;
    OneCallWeather OCW = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionsGranted = requestLocationPermission();

        if (permissionsGranted){
            updateLocation();
            updateGUI();
        }
    }

    public boolean updateLocation(){
        if (permissionsGranted){
            if (locationTracker == null)
                locationTracker = new LocationTracker(this);
            if (locationTracker.canGetLocation){
                locationTracker.getLocation();
                this.lat = locationTracker.getLatitude();
                this.lon = locationTracker.getLongitude();

                //wait for locationmanager to answer
                try {
                    while (this.lat == 0 && this.lon == 0) {
                        TimeUnit.MILLISECONDS.sleep(250);
                        this.lat = locationTracker.getLatitude();
                        this.lon = locationTracker.getLongitude();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public void updateGUI(){
        //get GUI elements
        TextView we = (TextView) findViewById(R.id.weather);
        TextView te = (TextView) findViewById(R.id.temp);
        TextView wi = (TextView) findViewById(R.id.wind);
        TextView cn = (TextView) findViewById(R.id.cityName);
        ImageView iv = (ImageView) findViewById(R.id.image);
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout);
        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(this);

        if(OCW == null){
            OCW = new OneCallWeather(lat,lon);
            //wait for the API response
            try {
                while (OCW.response.equals("")) {
                    OCW.getResponse();
                    TimeUnit.MILLISECONDS.sleep(250);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //update GUI
        we.setText(OCW.getWeather());
        te.setText(OCW.getTemperatures() + ", feels like: " + OCW.getFeelsLike());
        wi.setText(OCW.getWind());
        cn.setText(OCW.getCityName());
        iv.setImageResource(OCW.getWeatherID());
        cl.setBackgroundColor(OCW.getColor());
    }

    public void switchViewCities(View view){
        Intent intent = new Intent(this, CityWeatherActivity.class);
        startActivity(intent);
    }

    public void switchViewAlarm(View view){
        Intent intent = new Intent(this, AlarmManagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public boolean requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            permissionsGranted = true;
            updateLocation();
            updateGUI();
            return true;
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
            return false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Temperatures switched to " + (isChecked ? "Fahrenheit" : "Celsius"),
                Toast.LENGTH_SHORT).show();
        OCW.changeTemp(isChecked);
        updateGUI();
    }
}