package com.radiance01.clime;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.radiance01.clime.model.WeatherAdapter;
import com.radiance01.clime.model.WeatherReport;
import com.radiance01.clime.model.WeatherViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainContent extends AppCompatActivity{

    //http://api.openweathermap.org/data/2.5/forecast?lat=22.7196&lon=75.8577&appid=2006eb5abb29e4ea5527dcfcc30ff771

    LocationManager locationManager;
    LocationListener locationListener;
    Toolbar toolbar;

    Double lat = 22.7196;
    Double lon = 75.8577;

    TextView lay_date;
    TextView lay_city_country;
    TextView lay_current_temp;
    TextView lay_min_temp;
    TextView lay_weather;
    ImageView lay_weather_icon;

    ArrayList<WeatherReport> arrayList = new ArrayList<>();
    WeatherReport obj;

    WeatherAdapter weatherAdapter;
    RecyclerView recyclerView;
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.icon);
        volleyRequest();


        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        //recyclerView.setHasFixedSize(true);
        weatherAdapter = new WeatherAdapter(arrayList);
        recyclerView.setAdapter(weatherAdapter);

        lay_date = findViewById(R.id.lay_date);
        lay_city_country = findViewById(R.id.lay_city_country);
        lay_current_temp = findViewById(R.id.lay_current_temp);
        lay_min_temp = findViewById(R.id.lay_min_temp);
        lay_weather = findViewById(R.id.lay_weather);
        lay_weather_icon = findViewById(R.id.lay_weather_icon);


         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();
                volleyRequest();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


         //--------------------------getting explicit location permission from the user-------------------------------



        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT >= 23)
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }


    //---------------------------------------GET request to API from volley-----------------------------------------


    public void volleyRequest()
    {
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=2006eb5abb29e4ea5527dcfcc30ff771";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainContent.this, "Weather Info Updated successfully", Toast.LENGTH_SHORT).show();
                Log.v("clime","res:"+response.toString());

                try {

                    JSONObject city = response.getJSONObject("city");
                    String city_name = city.getString("name");
                    String city_country = city.getString("country");

                    JSONArray list = response.getJSONArray("list");

                    for( int i = 0;i<=32;i=i+8 )
                    {
                        JSONObject object = list.getJSONObject(i);
                        JSONObject main = object.getJSONObject("main");
                        Double temp = main.getDouble("temp");
                        Double temp_min = main.getDouble("temp_min");
                        Double temp_max = main.getDouble("temp_max");

                        JSONArray weather = object.getJSONArray("weather");
                        JSONObject weatherobj = weather.getJSONObject(0);
                        String weather_main = weatherobj.getString("main");
                        String description = weatherobj.getString("description");

                        String dt_txt = object.getString("dt_txt");

                        obj = new WeatherReport(city_name,city_country,temp.floatValue(),temp_min.floatValue(),temp_max.floatValue(),weather_main,description,dt_txt);
                        arrayList.add(obj);


                        update_ui();
                        weatherAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("clime","Error"+error.getLocalizedMessage());
                Toast.makeText(MainContent.this, "Failed to gather Info", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);

    }

    public void update_ui()
    {
           if(arrayList.size() > 0)
           {
               obj = arrayList.get(0);
               switch(obj.getWeather_main())
               {
                   case("Clear"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.clear_sky);
                       lay_weather.setText("Clear");
                       break;
                   }
                   case("Clouds"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.few_clouds);
                       lay_weather.setText("Clouds");
                       break;
                   }
                   case("scattered clouds"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.scattered_clouds);
                       lay_weather.setText("Scattered Clouds");
                       break;
                   }
                   case("broken clouds"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.broken_clouds);
                       lay_weather.setText("Broken Clouds");
                       break;
                   }
                   case("shower rain"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.shower_rain);
                       lay_weather.setText("Shower Rain");
                       break;
                   }
                   case("Rain"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.rain);
                       lay_weather.setText("Rain");
                       break;
                   }
                   case("Thunderstorm"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.thunderstorm);
                       lay_weather.setText("ThunderStorm");
                       break;
                   }
                   case("Snow"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.snow);
                       lay_weather.setText("Snow");
                       break;
                   }
                   case("Mist"):
                   {
                       lay_weather_icon.setImageResource(R.drawable.mist);
                       lay_weather.setText("Mist");
                       break;
                   }


               }
               String toshow_curr_temp = String.valueOf(obj.getTemp() + "°");
               String toshow_min_temp = String.valueOf(obj.getTemp_min() + "°");
               lay_date.setText(obj.getDt_txt());
               lay_current_temp.setText(toshow_curr_temp);
               lay_min_temp.setText(toshow_min_temp);
               lay_city_country.setText(obj.getCity_name()+","+obj.getCity_country());


           }


    }


}










