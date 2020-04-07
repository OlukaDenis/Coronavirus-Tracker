package com.mcdenny.coronavirusapp.data.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mcdenny.coronavirusapp.data.api.ApiClient;
import com.mcdenny.coronavirusapp.data.api.ApiService;
import com.mcdenny.coronavirusapp.data.db.CoronaCountryDao;
import com.mcdenny.coronavirusapp.data.db.CovidDatabase;
import com.mcdenny.coronavirusapp.data.db.GlobalDao;
import com.mcdenny.coronavirusapp.model.CoronaCountry;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CovidWorker extends Worker {
    private static final String TAG = "CovidWorker";
    private CovidDatabase database;
    private CoronaCountryDao countryDao;
    private GlobalDao globalDao;

    public CovidWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        database = CovidDatabase.getDatabase(context);
        countryDao = database.countryDao();
        globalDao = database.globalDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Log.i(TAG, "Fetching Data from Remote host");

        try {
            ApiService service = ApiClient.getApiService(ApiService.class);
            Call<List<CoronaCountry>> call = service.getAllCountries();
            Response<List<CoronaCountry>> response = call.execute();

            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                List<CoronaCountry> countryList = response.body();
                //delete all from the database
                countryDao.deleteAllCountries();
                //Populate new data
                countryDao.insertCoronaByCountry(countryList);

                Log.d(TAG, "Json string from the network " + countryList);
                return Result.success();
            } else {
                return Result.retry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error fetching data", e);
            return Result.failure();
        }


    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }

}
