package com.example.weather;

import java.util.concurrent.TimeUnit;
import org.json.*;

public class CurrentWeather {
    //class to access current weather data for any location including over 200,000 cities
    private double KtoC(double kelvin){
        kelvin -= 272.15;
        return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
    }

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

    public String getWeather(String city){
        JSONObject obj = null;
        try {
            obj = new JSONObject(getUpdate(city));
            JSONArray arr = obj.getJSONArray("weather");
            return arr.getJSONObject(0).getString("main") + ", " + arr.getJSONObject(0).getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getWind(String city){
        JSONObject obj = null;
        String toReturn = "Speed: ";
        try {
            obj = new JSONObject(getUpdate(city));
            toReturn += obj.getJSONObject("wind").getString("speed") + " direction: ";
            double degrees = obj.getJSONObject("wind").getDouble("deg");
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

    public String getCelsius(String city){
        JSONObject obj = null;
        try {
            obj = new JSONObject(getUpdate(city));
            double temp = KtoC(obj.getJSONObject("main").getDouble("temp"));
            return String.valueOf(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ""; //-250 = error
    }

    public String getFeelsLike(String city){
        JSONObject obj = null;
        try {
            obj = new JSONObject(getUpdate(city));
            double temp = KtoC(obj.getJSONObject("main").getDouble("feels_like"));
            return String.valueOf(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ""; //-250 = error
    }


}