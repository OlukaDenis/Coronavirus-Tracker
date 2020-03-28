package com.premar.coronavirusapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.data.repository.CovidRepository;
import com.premar.coronavirusapp.model.Covid;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidGlobalViewModel extends ViewModel{
    private CovidRepository repository;
    private static final String TAG = "CovidGlobalViewModel";

    public CovidGlobalViewModel(Application application){
        repository = new CovidRepository(application);
    }

    public Covid getCovidStats(){
        final Covid[] covidStats = new Covid[1];
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<Covid> call = service.getWorldStats();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                if (response.isSuccessful()){
                    covidStats[0] = response.body();
                    Log.d(TAG, "onResponse: "+ covidStats[0].getCases());
                }
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
        return covidStats[0];
    }


    public static class CovidGlobalViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public CovidGlobalViewModelFactory(Application application) {
            mApplication = application;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new CovidGlobalViewModel(mApplication);
        }
    }
}
