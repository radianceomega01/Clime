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
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.radiance01.clime.model.WeatherReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainContent extends AppCompatActivity{

    //http://openweathermap.org/data/2.5/forecast?lat=22.7196&lon=75.8577&appid=b6907d289e10d714a6e88b30761fae22

    LocationManager locationManager;
    LocationListener locationListener;

    Double lat = 22.7196;
    Double lon = 75.8577;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


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
            if(Build.VERSION.SDK_INT>23)
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
        String url = "http://openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=b6907d289e10d714a6e88b30761fae22";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainContent.this, "Response Recieved successfully", Toast.LENGTH_SHORT).show();
                Log.v("clime","res:"+response.toString());

                try {

                    ArrayList<WeatherReport> arrayList= new ArrayList<>();
                    JSONObject city = response.getJSONObject("city");
                    String city_name = city.getString("name");
                    String city_country = city.getString("country");

                    JSONArray list = response.getJSONArray("list");
                    int i;
                    for( i = 0;i<5;i=+8 );
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

                        WeatherReport obj = new WeatherReport(city_name,city_country,temp.intValue(),temp_min.intValue(),temp_max.intValue(),weather_main,description,dt_txt);
                        arrayList.add(obj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("clime","Error"+error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);

    }
}










