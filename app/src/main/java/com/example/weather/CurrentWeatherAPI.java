package com.example.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.concurrent.TimeUnit;

public class CurrentWeatherAPI {
    //class to access current weather data for any location including over 200,000 cities
    String URL = "api.openweathermap.org/data/2.5/weather?q=";
    String APIKey = "dac4c16af73f626902b58b1e8f36bb28";
    String URL2 = "&appid=";

    private String buildURL(String city){
        return "https://" + URL + city + URL2 + APIKey;
    }

    private String requestData(String city) throws Exception {
        String url = buildURL(city);
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            // TODO improve ErrorHandling
            throw new Exception("URL Problem");
        }
        HttpURLConnection con;
        StringBuilder response = new StringBuilder();
        try {
            con = (HttpURLConnection) obj.openConnection();
            con.connect();
            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            // TODO improve ErrorHandling
            System.out.println("Error "+e.getMessage());
            throw new Exception();
        }
        return response.toString();
    }

    public String getNews(){
        CurrentWeatherResponse response = null;
        String jsonResponse = null;
        try {
            jsonResponse = requestData("London");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(jsonResponse != null && !jsonResponse.isEmpty()){
            System.out.println("korte");
            System.out.print(jsonResponse);
        }
        //TODO improve Errorhandling
        return jsonResponse;
    }

}
