package com.example.weather;

import java.util.concurrent.TimeUnit;

public class OneCallWeather {
    //TODO interface for Weathers!
    private APIParserCoordinates api;
    public String response = "";
    private double lat;
    private double lon;
    String city;

    public OneCallWeather(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        //get data from API
        try {
            this.api = new APIParserCoordinates("onecall");
            api.execute(lat,lon);
            TimeUnit.SECONDS.sleep(2);
            this.response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public OneCallWeather(String city){
        this.city = city;
        try{
            this.api = new APIParserCoordinates("onecall");
            api.execute();
            TimeUnit.SECONDS.sleep(2);
            this.response = api.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getResponse() {
        response = api.getData();
    }

    private double KtoC(double kelvin){
        kelvin -= 272.15;
        return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
    }
}
