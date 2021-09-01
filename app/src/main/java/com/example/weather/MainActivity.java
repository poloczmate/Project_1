package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    double lat = 0;
    double lon = 0;
    LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean permissionsGranted = requestLocationPermission();
        if (permissionsGranted){
            locationTracker = new LocationTracker(this);
            if (locationTracker.canGetLocation){
                locationTracker.getLocation();
                this.lat = locationTracker.getLatitude();
                this.lon = locationTracker.getLongitude();
            }
        }
        try {
            while (this.lat == 0 && this.lon == 0) {
                TimeUnit.MILLISECONDS.sleep(500);
                this.lat = locationTracker.getLatitude();
                this.lon = locationTracker.getLongitude();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CurrentWeather CW = new CurrentWeather(lat,lon);
        //wait for the API response
        try {
            while (CW.response.equals("")) {
                CW.getResponse();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get GUI elements
        TextView we = (TextView) findViewById(R.id.weather);
        TextView te = (TextView) findViewById(R.id.temp);
        TextView wi = (TextView) findViewById(R.id.wind);
        TextView cn = (TextView) findViewById(R.id.cityName);

        //update GUI
        we.setText(CW.getWeather());
        te.setText("Celsius: " + CW.getCelsius() + ", feels like: " + CW.getFeelsLike() + " celsius");
        wi.setText(CW.getWind());
        cn.setText(CW.getCityName());
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
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
            return false;
        }
    }
}