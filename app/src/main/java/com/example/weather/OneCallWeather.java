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
    private boolean isCelsius = true;
    WeatherHour hourlyData[] = new WeatherHour[24];

    public OneCallWeather(){}

    public OneCallWeather(boolean isFahrenheit){
        isCelsius = !isFahrenheit;
    }

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

    public OneCallWeather(String city, Boolean isFahrenheit){
        this.isCelsius = !isFahrenheit;
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
    public double KtoCorF(double kelvin){
        if (isCelsius){
            kelvin -= 273.15;
            return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
        }else{
            kelvin = kelvin * 9/5 - 459.67;
            return (double) Math.round(kelvin * 100) / 100; //round it to 2 decimal
        }
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
            double speed = obj.getJSONObject("current").getDouble("wind_speed");
            toReturn += String.valueOf(speed) + " direction: ";

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
    public String getTemperatures(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            double temp = KtoCorF(obj.getJSONObject("current").getDouble("temp"));
            if (isCelsius){
                return String.valueOf(temp) + " Celsius";
            }else{
                return String.valueOf(temp) + " Fahrenheit";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public double getTemp(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            double temp = obj.getJSONObject("current").getDouble("temp");
            temp -= 273.15;
            return (double) Math.round(temp * 100) / 100;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -250; //-250 = error
    }

    //get how the temperature feels
    public String getFeelsLike(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            double temp = KtoCorF(obj.getJSONObject("current").getDouble("feels_like"));
            if (isCelsius){
                return String.valueOf(temp) + " Celsius";
            }else{
                return String.valueOf(temp) + " Fahrenheit";
            }
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

    public void changeTemp(boolean isFahrenheit){
        if (isFahrenheit){
            isCelsius = false;
        }else{
            isCelsius = true;
        }
    }

    public String getWear() {
        JSONObject obj = null;
        double temp = getTemp();
        try{
            obj = new JSONObject(response);
            JSONArray arr = obj.getJSONObject("current").getJSONArray("weather");
            String main = arr.getJSONObject(0).getString("main");

            if (getTemp() <= 15){
                if (main.equals("Thunderstorm")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof shoes or boots, umbrella";
                } else if (main.equals("Drizzle")){
                    return "Wintercoat, layered, warm coating, fur-lined, waterproof shoes or boots, umbrella";
                } else if (main.equals("Rain")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof shoes or boots, umbrella";
                } else if (main.equals("Snow")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof boots";
                } else if (main.equals("Clear")){
                    return "Wintercoat, layered, warm coating, fur-lined, shoes or boots";
                } else if (main.equals("Clouds")){
                    return "Wintercoat, layered, warm coating, fur-lined, shoes or boots";
                } else{
                    return "STAY AT HOME";
                }
            }else if (getTemp() > 15 && getTemp() <= 20){
                if (main.equals("Thunderstorm")){
                    return "Waterproof jacket, layered coating,  waterproof shoes, umbrella";
                } else if (main.equals("Drizzle")){
                    return "Jacket, layered coating,  waterproof shoes, umbrella";
                } else if (main.equals("Rain")){
                    return "Waterproof jacket, layered coating,  waterproof shoes, umbrella";
                } else if (main.equals("Snow")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof boots";
                } else if (main.equals("Clear")){
                    return "Light coat, layered coating, closed shoes";
                } else if (main.equals("Clouds")){
                    return "Light coat, layered coating, closed shoes";
                } else{
                    return "STAY AT HOME";
                }
            }else if (getTemp() > 20 && getTemp() <= 25){
                if (main.equals("Thunderstorm")){
                    return "Raincoat or windbreaker, slight t-shirt, sweater, waterproof shoes, umbrella";
                } else if (main.equals("Drizzle")){
                    return "Light coat, slight t-shirt, sweater, waterproof shoes, umbrella";
                } else if (main.equals("Rain")){
                    return "Light coat, slight t-shirt, sweater, waterproof shoes, umbrella";
                } else if (main.equals("Snow")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof boots";
                } else if (main.equals("Clear")){
                    return "Light coat, sweater, t-shirt, oxford shoes";
                } else if (main.equals("Clouds")){
                    return "Slight t-shirt, sandals";
                } else{
                    return "STAY AT HOME";
                }
            }else{
                if (main.equals("Thunderstorm")){
                    return "Raincoat or windbreaker, slight t-shirt, waterproof shoes, umbrella";
                } else if (main.equals("Drizzle")){
                    return "Sweater, slight t-shirt, waterproof shoes, umbrella";
                } else if (main.equals("Rain")){
                    return "Raincoat, slight t-shirt, sweater, waterproof shoes, umbrella ";
                } else if (main.equals("Snow")){
                    return "Waterproof wintercoat, layered, warm coating, fur-lined, waterproof boots";
                } else if (main.equals("Clear")){
                    return "Slight t-shirt, sandals";
                } else if (main.equals("Clouds")){
                    return "Slight t-shirt, sandals";
                } else{
                    return "STAY AT HOME";
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return "";
    }

    public boolean getIsCelsius(){
        return isCelsius;
    }
}
