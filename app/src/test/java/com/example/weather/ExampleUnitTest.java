package com.example.weather;

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

/*

+1 ha OneCallWeather masik konstruktort hivjuk, ahol megadjuk milyen mertekegyseggel induljon is legyen letesztelve.
 */



public class ExampleUnitTest {

    String OneCallWeatherResponse = "";
    String CurrentWeatherResponse = "";
    String WeatherHourResponse = "";

    @Before public void File_reader() throws IOException {
        File file = new File(
                "src/test/java/com/example/weather/onecall_weather_response.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine(); ;
        while (st != null) {
            OneCallWeatherResponse += st;
            st = br.readLine();
        }

        File file2 = new File("src/test/java/com/example/weather/current_weather_response.txt");
        BufferedReader br2 = new BufferedReader(new FileReader(file2));
        st = br2.readLine();
        while (st != null) {
            CurrentWeatherResponse += st;
            st = br.readLine();
        }

        File file3 = new File("src/test/java/com/example/weather/WeatherHour.txt");
        BufferedReader br3 = new BufferedReader(new FileReader(file3));
        st = br3.readLine();
        while (st != null) {
            WeatherHourResponse += st;
            st = br.readLine();
        }
    }

    @Test
    public void Kelvin_Test(){
        OneCallWeather OCW = new OneCallWeather();
        assertEquals(10.00, OCW.KtoCorF(283.15), 0);
    }

    @Test
    public void Kelvin_Test2(){
        OneCallWeather OCW =  new OneCallWeather();
        assertEquals(-10, OCW.KtoCorF(263.15),0);

    }

    @Test
    public void feels_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        assertEquals( "11.11 Celsius",OCW.getFeelsLike());
    }

    @Test
    public void getCelsius_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        assertEquals("12.12 Celsius" , OCW.getTemperatures());
    }

    @Test
    public void getWeather_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        assertEquals(2131165287 , OCW.getWeatherID());
    }

    @Test
    public void Weather_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        assertEquals("Clear, clear sky" , OCW.getWeather());
    }

    @Test
    public void Color_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        assertEquals(Color.parseColor("#81B4EA") , OCW.getColor());
    }

    @Test
    public void getWind_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        String expected = "Speed: " + "3.09" + " direction: " + "NNE";
        assertEquals(expected, OCW.getWind());
    }

    @Test
    public void city_test(){
        CurrentWeather CW = new CurrentWeather();
        CW.response = CurrentWeatherResponse;
        String expected = "Budapest";
        assertEquals(expected,CW.getCityName());
    }

    @Test
    public void getLon_test(){
        CurrentWeather CW = new CurrentWeather();
        CW.response = WeatherHourResponse;
        double expected = 13.404;
        assertEquals(expected,CW.getLon(),0);
    }

    @Test
    public void getLat_test(){
        CurrentWeather CW = new CurrentWeather();
        CW.response = WeatherHourResponse;
        double expected = 52.520;
        assertEquals(expected,CW.getLat(), 0.001);
    }

    @Test
    public void cityBerlin_test(){
        CurrentWeather CW = new CurrentWeather();
        CW.response = WeatherHourResponse;
        String expected = "Berlin";
        assertEquals(expected,CW.getCityName());
    }

    @Test
    public void fahrenheit_to_celsius_test(){
        OneCallWeather OCW = new OneCallWeather();
        OCW.response = OneCallWeatherResponse;
        OCW.changeTemp(true); //switch to fahrenheit
        assertEquals("53.82 Fahrenheit" , OCW.getTemperatures());
    }

    @Test
    public void start_with_fahrenheit(){
        OneCallWeather OCW = new OneCallWeather(true);
        OCW.response = OneCallWeatherResponse;
        assertEquals(OCW.getIsCelsius(), false);
    }
}