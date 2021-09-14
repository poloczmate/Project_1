package com.example.weather;

import java.util.concurrent.TimeUnit;
import org.json.*;

public class CurrentWeather {
    //class to access current weather data for any location including over 200,000 cities
    private APIParserCoord api;
    private APIParserCity apicity;
    public String response = "";

    public CurrentWeather(String city){
        //todo new apiparser
        try {
            this.apicity = new APIParserCity();
            apicity.execute(city);
            TimeUnit.SECONDS.sleep(2);
            while (response.equals("")) this.response = apicity.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public CurrentWeather(double lat, double lon){
        //get data from API
        try {
            this.api = new APIParserCoord("weather");
            api.execute(lat,lon);
            TimeUnit.SECONDS.sleep(2);
            while (response.equals("")) this.response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getResponse() {
        response = api.getData();
    }

    public String getCityName(){
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            return obj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public double getLon() {
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            return obj.getJSONObject("coord").getDouble("lon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getLat() {
        JSONObject obj = null;
        try{
            obj = new JSONObject(response);
            return obj.getJSONObject("coord").getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
