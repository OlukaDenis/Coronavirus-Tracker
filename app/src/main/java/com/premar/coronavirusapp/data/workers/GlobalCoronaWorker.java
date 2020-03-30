package com.premar.coronavirusapp.data.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.data.db.CoronaCountryDao;
import com.premar.coronavirusapp.data.db.CovidDatabase;
import com.premar.coronavirusapp.data.db.GlobalDao;
import com.premar.coronavirusapp.model.Covid;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class GlobalCoronaWorker extends Worker {
    private static final String TAG = "GlobalCoronaWorker";
    private CovidDatabase database;
    private GlobalDao globalDao;

    public GlobalCoronaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        database = CovidDatabase.getDatabase(context);
        globalDao = database.globalDao();
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            ApiService service = ApiClient.getApiService(ApiService.class);
            Call<Covid> call = service.getWorldStats();
            Response<Covid> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                Covid covid = response.body();
                globalDao.deleteAllCovids();
                globalDao.insertCovids(covid);
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
