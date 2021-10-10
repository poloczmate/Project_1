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

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BuffferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st = br.readLine(); ;
        // Consition holds true till

        // there is character in a string
        while (st != null) {

            response+=st;
            st=br.readLine();
        }

        System.out.println(response);
    }





    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Kelvin_Test(){
        OneCallWeather OCW = new OneCallWeather();
        assertEquals(10.00, OCW.KtoC(283.15), 0);
    }

    @Test
    public void Kelvin_Test2(){
        OneCallWeather OCW =  new OneCallWeather();
        assertEquals(20, OCW.KtoC(566.3),0);

    }

    @Test
    public  void wind_test1(){
        OneCallWeather OCW = new OneCallWeather();
        assertEquals("N", "N", OCW.getWind());

    }

    @Test
    public void feels_test(){
        OneCallWeather OCW  =   new OneCallWeather();
        OCW.response    = this.response;
        assertEquals( "test failed","11.11",OCW.getFeelsLike());


    }


    @Test
    public void hour_test(){
        OneCallWeather OCW = new OneCallWeather();
        assertFalse(10,OCW.getHourlyWeather(10),0);

    }

    @Test
    public void  color_test(){
        OneCallWeather OCW  =   new OneCallWeather();
        assertEquals(Color.GRAY, OCW.getColor("Thunderstorm"));

    }



}