package com.premar.coronavirusapp.view.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.CountryInfo;

import java.util.List;

import static com.premar.coronavirusapp.Utils.Constants.formatNumber;
import static com.premar.coronavirusapp.view.ui.home.HomeFragment.countryInfoSize;

public class CountriesViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CountriesViewHolder";
    private TextView confirmed, deaths, recovered, countryName, confirmedToday, deathsToday, countryFlag;

    public CountriesViewHolder(@NonNull View itemView) {
        super(itemView);
        confirmed = itemView.findViewById(R.id.country_confirmed);
        deaths = itemView.findViewById(R.id.country_deaths);
        recovered = itemView.findViewById(R.id.country_recovered);
        countryName = itemView.findViewById(R.id.country_name);
        confirmedToday = itemView.findViewById(R.id.country_confirmed_today);
        deathsToday = itemView.findViewById(R.id.country_deaths_today);
        countryFlag = itemView.findViewById(R.id.country_flag);

    }

    public void bindTO(CoronaCountry country){
        confirmed.setText(formatNumber(country.getCases()));
        deaths.setText(formatNumber(country.getDeaths()));
        recovered.setText(formatNumber(country.getRecovered()));
        countryName.setText(country.getCountry());
        confirmedToday.setText(formatNumber(country.getTodayCases()));
        deathsToday.setText(formatNumber(country.getTodayDeaths()));

        CountryInfo countryInfos = country.getCountryInfo();
        countryInfoSize = countryInfos.toString();
    }
}
