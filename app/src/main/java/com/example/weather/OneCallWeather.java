package com.example.weather;

import java.util.concurrent.TimeUnit;

public class OneCallWeather {
    //TODO interface for Weathers!
    private APIParserCoordinates apico = null;
    private ApiParserCity apici = null;
    public String response = "";
    private double lat;
    private double lon;
    String city;

    public OneCallWeather(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        //get data from API
        try {
            this.apico = new APIParserCoordinates("onecall");
            apico.execute(lat,lon);
            TimeUnit.SECONDS.sleep(2);
            this.response = apico.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public OneCallWeather(String city){
        this.city = city;
        try{
            this.apici = new ApiParserCity("onecall");
            apici.execute(city);
            TimeUnit.SECONDS.sleep(2);
            this.response = apici.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getResponse() {
        if (apico.equals(null)){
            response = apici.getData();
        }else{
            response = apico.getData();
        }
    }

    private double KtoC(double kelvin){
        kelvin -= 272.15;
        return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
    }
}
