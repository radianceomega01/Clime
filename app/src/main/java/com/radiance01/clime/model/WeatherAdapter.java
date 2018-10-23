package com.radiance01.clime.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiance01.clime.R;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder>
{
    ArrayList<WeatherReport> weatherarray;

    public WeatherAdapter(ArrayList<WeatherReport> weatherarray) {
        this.weatherarray = weatherarray;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new WeatherViewHolder(card);

    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {

        WeatherReport report = weatherarray.get(position);
        holder.update_cardlist(report);

    }

    @Override
    public int getItemCount() {
        return weatherarray.size();
    }
}
