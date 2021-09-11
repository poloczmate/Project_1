package com.example.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

//class to store weather data hour to hour
public class WeatherHour {
    double hour;
    String weather;
    double wind_speed;
    String wind_dir;
    double temperature;
    double feels_like;

    private double KtoC(double kelvin){
        kelvin -= 272.15;
        return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
    }

    private String getWind_dir(double degrees){
        String toReturn = "";
        if (degrees >= 348.75 || degrees <= 11.25){
            toReturn = "N";
        }else if(degrees >= 11.25 || degrees <= 33.75){
            toReturn = "NNE";
        }else if(degrees >= 33.75 || degrees <= 56.25){
            toReturn = "NE";
        }else if(degrees >= 56.25 || degrees <= 78.75){
            toReturn = "ENE";
        }else if(degrees >= 78.75 || degrees <= 101.25){
            toReturn = "E";
        }else if(degrees >= 101.25 || degrees <= 123.75){
            toReturn = "ESE";
        }else if(degrees >= 123.75 || degrees <= 146.25){
            toReturn = "SE";
        }else if(degrees >= 146.25 || degrees <= 168.75){
            toReturn = "SSE";
        }else if(degrees >= 168.75 || degrees <= 191.25){
            toReturn = "S";
        }else if(degrees >= 191.25 || degrees <= 213.75){
            toReturn = "SSW";
        }else if(degrees >= 213.75 || degrees <= 236.25){
            toReturn = "SW";
        }else if(degrees >= 236.25 || degrees <= 258.75){
            toReturn = "WSW";
        }else if(degrees >= 258.75 || degrees <= 281.25){
            toReturn = "W";
        }else if(degrees >= 281.25 || degrees <= 303.75){
            toReturn = "WNW";
        }else if(degrees >= 303.75 || degrees <= 326.25){
            toReturn = "NW";
        }else if(degrees >= 326.25 || degrees <= 348.75){
            toReturn = "NW";
        }
        return toReturn;
    }

    public WeatherHour(JSONObject jo){
        try {
            weather = jo.getJSONArray("weather").getJSONObject(0).getString("main") +
                    ", " + jo.getJSONArray("weather").getJSONObject(0).getString("description");

            wind_speed = jo.getDouble("wind_speed");

            double degrees = jo.getDouble("wind_deg");
            wind_dir = getWind_dir(degrees);
            temperature = KtoC(jo.getDouble("temp"));
            feels_like = KtoC(jo.getDouble("feels_like"));

            Date date = new Date((long)jo.getLong("dt")*1000);
            hour = date.getHours();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
