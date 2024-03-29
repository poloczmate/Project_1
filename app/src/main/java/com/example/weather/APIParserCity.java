package com.example.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIParserCity extends AsyncTask<String, Number, String> {
    private String URL;
    private String APIKey = "dac4c16af73f626902b58b1e8f36bb28";
    private String URL2 = "&appid=";
    private String returnString = "";

    public APIParserCity(){ //weather for CurrentWeather onecall for OneCallWeather
        URL = "api.openweathermap.org/data/2.5/weather?q=";
    }

    private String buildURL(String city){
        return "https://" + URL + city + URL2 + APIKey;
    }

    public String getData(){
        return returnString;
    }

    @Override
    protected String doInBackground(String... cities) {
        String url = buildURL(cities[0]);
        java.net.URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            // TODO improve ErrorHandling
            System.out.println("URL Exception");
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
        }
        returnString = response.toString();
        return response.toString();
    }

    @Override
    protected void onPreExecute() {
        // Runs on UI thread- Any code you wants
        // to execute before web service call. Put it here.
        // Eg show progress dialog
    }

    @Override
    protected void onProgressUpdate(Number... numbers) {
        super.onProgressUpdate(numbers);
    }

    @Override
    protected void onPostExecute(String resp) {

    }
}
