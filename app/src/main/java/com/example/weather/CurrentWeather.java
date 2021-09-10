package com.example.weather;

import java.util.concurrent.TimeUnit;
import org.json.*;

public class CurrentWeather {
    //class to access current weather data for any location including over 200,000 cities
    private APIParserCoordinates api;
    public String response = "";

    public CurrentWeather(double lat, double lon){
        //get data from API
        try {
            this.api = new APIParserCoordinates("weather");
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
}
