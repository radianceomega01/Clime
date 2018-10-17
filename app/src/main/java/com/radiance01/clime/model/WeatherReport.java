package com.radiance01.clime.model;

public class WeatherReport {

    String city_name;
    String city_country;
    int temp;
    int temp_min;
    int temp_max;
    String weather_main;
    String description;
    String dt_txt;

    public WeatherReport(String city_name, String city_country, int temp, int temp_min, int temp_max, String weather_main, String description, String dt_txt) {
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

    public int getTemp() {
        return temp;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public String getDescription() {
        return description;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
