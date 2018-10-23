package com.radiance01.clime.model;

import com.radiance01.clime.MainContent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherReport {

    String city_name;
    String city_country;
    float temp;
    float temp_min;
    float temp_max;
    String weather_main;
    String description;
    String dt_txt;

    public WeatherReport(String city_name, String city_country, float temp, float temp_min, float temp_max, String weather_main, String description, String dt_txt) {
        this.city_name = city_name;
        this.city_country = city_country;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.weather_main = weather_main;
        this.description = description;
        this.dt_txt = dt_txt;
    }


    public String getCity_name() {
        return city_name;
    }

    public String getCity_country() {
        return city_country;
    }

    public double getTemp() {
        return Math.floor((temp-273)*100)/100;
    }

    public double getTemp_min() {
        return Math.floor((temp_min-273)*100)/100;
    }

    public double getTemp_max() {
        return Math.floor((temp_max-273)*100)/100;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public String getDescription() {
        return description;
    }

    public String getDt_txt() {
        Date date = new Date();
        dt_txt = new SimpleDateFormat("EEE, MMM d", Locale.ENGLISH).format(date);
        return dt_txt;
    }
}
