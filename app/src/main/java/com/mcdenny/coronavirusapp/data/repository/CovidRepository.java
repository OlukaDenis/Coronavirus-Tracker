package com.mcdenny.coronavirusapp.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mcdenny.coronavirusapp.data.db.CoronaCountryDao;
import com.mcdenny.coronavirusapp.data.db.CovidDatabase;
import com.mcdenny.coronavirusapp.data.db.GlobalDao;
import com.mcdenny.coronavirusapp.model.CoronaCountry;
import com.mcdenny.coronavirusapp.model.Covid;

import java.util.List;

public class CovidRepository {
    private CovidDatabase database;
    private CoronaCountryDao mDao;
    private GlobalDao globalDao;
    private static final String TAG = "CovidRepository";

    public CovidRepository(Application application){
        database = CovidDatabase.getDatabase(application);
        mDao = database.countryDao();
        globalDao = database.globalDao();
    }

    public LiveData<List<CoronaCountry>> getAllCountries(){
        return mDao.getAllCountries();
    }

    public Covid getGlobalStat(String orderBy){
        return globalDao.globalStats(orderBy);
    }


    public CoronaCountry ugandaStatus(String country){
        return mDao.getOneCountry(country);
    }

    public void fetchGlobalStatus() {

    }
}
