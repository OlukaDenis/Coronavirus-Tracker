package com.premar.coronavirusapp.data.repository;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.data.db.CoronaCountryDao;
import com.premar.coronavirusapp.data.db.CovidDatabase;
import com.premar.coronavirusapp.data.db.GlobalDao;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.Covid;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.coronavirusapp.Utils.Constants.UGANDA;

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
