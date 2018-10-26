package com.radiance01.clime.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.radiance01.clime.R;

import java.text.ParseException;

public class WeatherViewHolder extends RecyclerView.ViewHolder
{
    ImageView card_icon;
    TextView card_day;
    TextView card_weather;
    TextView card_maxtemp;
    TextView card_mintemp;


    public WeatherViewHolder(View itemView) {
        super(itemView);


        card_icon = itemView.findViewById(R.id.card_icon);
        card_day = itemView.findViewById(R.id.card_day);
        card_weather = itemView.findViewById(R.id.card_weather);
        card_maxtemp = itemView.findViewById(R.id.card_maxtemp);
        card_mintemp = itemView.findViewById(R.id.card_mintemp);
    }
    public void update_cardlist(WeatherReport report)
    {
        switch(report.getWeather_main())
        {
            case("Clear"):
            {
                card_icon.setImageResource(R.drawable.clear_sky);
                card_weather.setText("Clear Sky");
                break;
            }
            case("Clouds"):
            {
                card_icon.setImageResource(R.drawable.few_clouds);
                card_weather.setText("Few Clouds");
                break;
            }
            case("scattered clouds"):
            {
                card_icon.setImageResource(R.drawable.scattered_clouds);
                card_weather.setText("Scattered Clouds");
                break;
            }
            case("broken clouds"):
            {
                card_icon.setImageResource(R.drawable.broken_clouds);
                card_weather.setText("Broken Clouds");
                break;
            }
            case("shower rain"):
            {
                card_icon.setImageResource(R.drawable.shower_rain);
                card_weather.setText("Shower Rain");
                break;
            }
            case("Rain"):
            {
                card_icon.setImageResource(R.drawable.rain);
                card_weather.setText("Rain");
                break;
            }
            case("Thunderstorm"):
            {
                card_icon.setImageResource(R.drawable.thunderstorm);
                card_weather.setText("ThunderStorm");
                break;
            }
            case("Snow"):
            {
                card_icon.setImageResource(R.drawable.snow);
                card_weather.setText("Snow");
                break;
            }
            case("Mist"):
            {
                card_icon.setImageResource(R.drawable.mist);
                card_weather.setText( "Mist");
                break;
            }
        }
        try {
            card_day.setText(report.getDt_txt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        card_maxtemp.setText(String.valueOf(report.getTemp_max()));
        card_mintemp.setText(String.valueOf(report.getTemp_min()));
    }
}