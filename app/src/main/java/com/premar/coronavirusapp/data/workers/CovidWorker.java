package com.premar.coronavirusapp.data.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.data.db.CoronaCountryDao;
import com.premar.coronavirusapp.data.db.CovidDatabase;
import com.premar.coronavirusapp.data.db.GlobalDao;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.Covid;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
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
