package com.mcdenny.coronavirusapp.view.ui.hospitals;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mcdenny.coronavirusapp.data.repository.CovidRepository;
import com.mcdenny.coronavirusapp.model.Hospital;

import java.util.List;

public class HospitalViewModel extends ViewModel {
    private CovidRepository repository;

    public HospitalViewModel(Application application) {
        repository = new CovidRepository(application);
    }

    public LiveData<List<Hospital>> getAllHospitals(){
        return repository.getAllHospitals();
    }
}
