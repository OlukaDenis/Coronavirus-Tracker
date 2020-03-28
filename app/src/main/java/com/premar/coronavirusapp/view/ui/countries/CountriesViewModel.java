package com.premar.coronavirusapp.view.ui.countries;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.premar.coronavirusapp.data.repository.CovidRepository;
import com.premar.coronavirusapp.model.CoronaCountry;

import java.util.List;

public class CountriesViewModel extends ViewModel {

    private CovidRepository repository;

    public CountriesViewModel(Application application) {
       repository = new CovidRepository(application);
    }

    public LiveData<List<CoronaCountry>> getStatsByCountry() {
        return repository.getAllCountries();
    }



}