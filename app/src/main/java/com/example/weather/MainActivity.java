package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
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

        LocationTracker locationTracker = new LocationTracker(this);
        if (locationTracker.canGetLocation){
            locationTracker.getLocation();
            System.out.println(locationTracker.getLatitude());
            System.out.println(locationTracker.getLongitude());

        }else{
            System.out.println("Unable to find");
        }
    }
}