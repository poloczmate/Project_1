package com.example.weather;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class OneCallWeather {
    private APIParserCoord api;
    public String response = "";
    private String cityName;
    WeatherHour hourlyData[] = new WeatherHour[24];

    public OneCallWeather(String city){
        CurrentWeather CW = new CurrentWeather(city);
        try {
            double lat = CW.getLat();
            double lon = CW.getLon();
            TimeUnit.SECONDS.sleep(2);
            this.api = new APIParserCoord("onecall");
            api.execute(lat,lon);
            TimeUnit.SECONDS.sleep(2);
            this.response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.cityName = CW.getCityName();
        getHourlyWeather();

    }

    public OneCallWeather(double lat, double lon){
        //CurrentWeatherAPI because we need the name of the city
        CurrentWeather CW = new CurrentWeather(lat,lon);

        //get data from API
        try {
            this.api = new APIParserCoord("onecall");
            api.execute(lat,lon);
            TimeUnit.SECONDS.sleep(2);
            this.response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.cityName = CW.getCityName();
        getHourlyWeather();
    }

    public void getResponse() {
        response = api.getData();
    }

    //convert kelvin to celsius
    private double KtoC(double kelvin){
        kelvin -= 272.15;
        return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
    }

    //get weather description
    public String getWeather(){
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            JSONArray arr = obj.getJSONObject("current").getJSONArray("weather");
            return arr.getJSONObject(0).getString("main") + ", " + arr.getJSONObject(0).getString("description");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "";
    }

    public int getWeatherID(){
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            JSONArray arr = obj.getJSONObject("current").getJSONArray("weather");
            String main = arr.getJSONObject(0).getString("main");

            if (main.equals("Thunderstorm")){
                return R.drawable.thunderstorm;
            } else if (main.equals("Drizzle")){
                return R.drawable.drizzle;
            } else if (main.equals("Rain")){
                return R.drawable.rain;
            } else if (main.equals("Snow")){
                return R.drawable.snow;
            } else if (main.equals("Clear")){
                return R.drawable.clear;
            } else if (main.equals("Clouds")){
                return R.drawable.cloud;
            } else{
                return R.drawable.extreme;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }

    //get wind speed and direction
    public String getWind(){
        JSONObject obj = null;
        String toReturn = "Speed: ";
        try {
            obj = new JSONObject(response);
            toReturn += obj.getJSONObject("current").getString("wind_speed") + " direction: ";

            double degrees = obj.getJSONObject("current").getDouble("wind_deg");
            if (degrees >= 348.75 || degrees <= 11.25){
                toReturn += "N";
            }else if(degrees >= 11.25 || degrees <= 33.75){
                toReturn += "NNE";
            }else if(degrees >= 33.75 || degrees <= 56.25){
                toReturn += "NE";
            }else if(degrees >= 56.25 || degrees <= 78.75){
                toReturn += "ENE";
            }else if(degrees >= 78.75 || degrees <= 101.25){
                toReturn += "E";
            }else if(degrees >= 101.25 || degrees <= 123.75){
                toReturn += "ESE";
            }else if(degrees >= 123.75 || degrees <= 146.25){
                toReturn += "SE";
            }else if(degrees >= 146.25 || degrees <= 168.75){
                toReturn += "SSE";
            }else if(degrees >= 168.75 || degrees <= 191.25){
                toReturn += "S";
            }else if(degrees >= 191.25 || degrees <= 213.75){
                toReturn += "SSW";
            }else if(degrees >= 213.75 || degrees <= 236.25){
                toReturn += "SW";
            }else if(degrees >= 236.25 || degrees <= 258.75){
                toReturn += "WSW";
            }else if(degrees >= 258.75 || degrees <= 281.25){
                toReturn += "W";
            }else if(degrees >= 281.25 || degrees <= 303.75){
                toReturn += "WNW";
            }else if(degrees >= 303.75 || degrees <= 326.25){
                toReturn += "NW";
            }else if(degrees >= 326.25 || degrees <= 348.75){
                toReturn += "NW";
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return toReturn;
    }

    //get the temperature
    public String getCelsius(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            double temp = KtoC(obj.getJSONObject("current").getDouble("temp"));
            return String.valueOf(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ""; //-250 = error
    }

    //get how the temperature feels
    public String getFeelsLike(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            double temp = KtoC(obj.getJSONObject("current").getDouble("feels_like"));
            return String.valueOf(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ""; //-250 = error
    }
    //return the name of the city
    public String getCityName(){
        return cityName;
    }

    private void getHourlyWeather(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            for (int i = 0; i < 24; i++){
                JSONObject jo = obj.getJSONArray("hourly").getJSONObject(i);
                hourlyData[i] = new WeatherHour(jo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getColor() {
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            JSONArray arr = obj.getJSONObject("current").getJSONArray("weather");
            String main = arr.getJSONObject(0).getString("main");

            if (main.equals("Thunderstorm")){
                return Color.GRAY;
            } else if (main.equals("Drizzle")){
                return Color.LTGRAY;
            } else if (main.equals("Rain")){
                return Color.GRAY;
            } else if (main.equals("Snow")){
                return Color.WHITE;
            } else if (main.equals("Clear")){
                return Color.parseColor("#81B4EA");
            } else if (main.equals("Clouds")){
                return Color.LTGRAY;
            } else{
                return Color.YELLOW;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
