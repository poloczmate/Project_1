package com.example.weather;

import java.util.concurrent.TimeUnit;
import org.json.*;

public class CurrentWeather {
    //class to access current weather data for any location including over 200,000 cities

    private String getUpdate(String city){
        String response = null;
        try {
            APIParser api = new APIParser();
            api.execute(city);
            TimeUnit.SECONDS.sleep(1);
            response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //TODO improve Errorhandling
        return response;
    }


}
