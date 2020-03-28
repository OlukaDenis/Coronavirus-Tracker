package com.premar.coronavirusapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.view.viewholder.CountriesViewHolder;

import java.util.List;

public class CoronaCountryAdapter extends RecyclerView.Adapter<CountriesViewHolder> {
    private Context context;
    private List<CoronaCountry> countryList;

    public CoronaCountryAdapter(Context context, List<CoronaCountry> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stats, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        CoronaCountry country = countryList.get(position);
        if (country != null){
            holder.bindTO(country);
        }
    }

    @Override
    public int getItemCount() {
        return countryList==null?0 : countryList.size();
    }
}
