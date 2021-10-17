package com.example.weather;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.Color;

import java.io.*;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



public class ExampleUnitTest {

    String response="";

    @Before public void File_reader() throws IOException {
        File file = new File(
                "src/test/java/com/example/weather/resp_data.txt");

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String st = br.readLine(); ;

        while (st != null) {

            response += st;

            st = br.readLine();
        }
    }

    @Test
    public void Kelvin_Test(){
        OneCallWeather OCW = new OneCallWeather();
        assertEquals(10.00, OCW.KtoC(283.15), 0);
    }

    @Test
    public void Kelvin_Test2(){
        OneCallWeather OCW =  new OneCallWeather();
        assertEquals(-10, OCW.KtoC(263.15),0);

    }

    @Test
    public void feels_test(){
        OneCallWeather OCW  =   new OneCallWeather();
        OCW.response    = this.response;
        assertEquals( "11.11",OCW.getFeelsLike());
    }

    @Test
    public void getCelsius_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response    =   this.response;
        assertEquals("12.12" , OCW.getCelsius());
    }

    @Test
    public void getWeather_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response    =   this.response;
        assertEquals(2131165287 , OCW.getWeatherID());
    }

    @Test
    public void Weather_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response    =   this.response;
        assertEquals("Clear, clear sky" , OCW.getWeather());
    }

    //TODO
    @Test
    public void Color_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = this.response;
        assertEquals(Color.parseColor("#81B4EA") , OCW.getColor());
    }
    
    //TODO
    @Test
    public void getWind_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response    =   this.response;
        String expected = "Speed: " + "3.09" + " direction: " + "NNE";
        assertEquals(expected, OCW.getWind());
    }


}