package com.premar.coronavirusapp.data.repository;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.data.db.CoronaCountryDao;
import com.premar.coronavirusapp.data.db.CovidDatabase;
import com.premar.coronavirusapp.model.CoronaCountry;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.coronavirusapp.Utils.Constants.UGANDA;

public class CovidRepository {
    private CovidDatabase database;
    private CoronaCountryDao mDao;
    private static final String TAG = "CovidRepository";

    public CovidRepository(Application application){
        database = CovidDatabase.getDatabase(application);
        mDao = database.countryDao();
    }

    public LiveData<List<CoronaCountry>> getAllCountries(){
        fetchAllCountries();
        return mDao.getAllCountries();
    }

    private void fetchAllCountries(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<List<CoronaCountry>> call = service.getAllCountries();
        call.enqueue(new Callback<List<CoronaCountry>>() {
            @Override
            public void onResponse(Call<List<CoronaCountry>> call, Response<List<CoronaCountry>> response) {
                if (response.isSuccessful()) {
                    List<CoronaCountry> countryList = response.body();
                    if (countryList != null) {
                        mDao.insertCoronaByCountry(countryList);
                    }
                    Log.d(TAG, "Corona Countries: "+countryList.size());
                }
            }

            @Override
            public void onFailure(Call<List<CoronaCountry>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }

    public void getUgandaStats(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<CoronaCountry> call = service.getOneCountry(UGANDA);
        call.enqueue(new Callback<CoronaCountry>() {
            @Override
            public void onResponse(Call<CoronaCountry> call, Response<CoronaCountry> response) {
                if (response.isSuccessful()){
                    CoronaCountry uganda = response.body();
                    Log.d(TAG, "Uganda Stats: "+uganda.getCases());
                }
            }

            @Override
            public void onFailure(Call<CoronaCountry> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }

    public CoronaCountry ugandaStatus(String country){
        return mDao.getOneCountry(country);
    }
}
