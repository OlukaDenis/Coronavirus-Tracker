package com.premar.coronavirusapp.view.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.Covid;
import com.premar.coronavirusapp.view.ui.countries.CountriesFragment;
import com.premar.coronavirusapp.viewmodel.CovidGlobalViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.coronavirusapp.Utils.Constants.UGANDA;
import static com.premar.coronavirusapp.Utils.Constants.formatNumber;

public class HomeFragment extends Fragment {
    public static String countryInfoSize = "";

    private TextView tvCases, tvDeaths, tvRecovered, ugCases, ugDeaths, ugRecovered, ugCasesToday, ugDeathsToday, moreCountries;
    private HomeViewModel viewModel;
    private CountriesFragment countriesFragment;
    private FragmentManager fragmentManager;
    private Covid covid;
    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //reference
        tvCases = root.findViewById(R.id.cases);
        tvDeaths = root.findViewById(R.id.deaths);
        tvRecovered = root.findViewById(R.id.recovered);
        ugCases = root.findViewById(R.id.uganda_cases);
        ugDeaths = root.findViewById(R.id.uganda_deaths);
        ugRecovered = root.findViewById(R.id.uganda_recovered);
        moreCountries = root.findViewById(R.id.more_countries);
        ugCasesToday = root.findViewById(R.id.uganda_cases_today);
        ugDeathsToday = root.findViewById(R.id.uganda_deaths_today);

        moreCountries.setOnClickListener(v -> openCountryFragement());

        HomeViewModelFactory factory = new HomeViewModelFactory(this.getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        getStats();
        getUgandaCoronaStats();

        Log.d(TAG, "countryInfoSize: "+countryInfoSize);
        return root;

    }

    private void openCountryFragement() {
        countriesFragment = new CountriesFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, countriesFragment);
        transaction.commit();
    }



    private void populateStats(Covid covid) {

        tvCases.setText(formatNumber(covid.getCases()));
        tvDeaths.setText(formatNumber(covid.getDeaths()));
        tvRecovered.setText(formatNumber(covid.getRecovered()));
    }

    private void ugandaStats(CoronaCountry country){
        ugCases.setText(formatNumber(country.getCases()));
        ugDeaths.setText(formatNumber(country.getDeaths()));
        ugRecovered.setText(formatNumber(country.getRecovered()));

        Resources res = getResources();

        ugCasesToday.setText(String.format(res.getString(R.string.today), country.getTodayCases()));
        ugDeathsToday.setText(String.format(res.getString(R.string.today), country.getTodayDeaths()));
    }

    private void getStats(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<Covid> call = service.getWorldStats();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, Response<Covid> response) {
                if (response.isSuccessful()){
                    Covid covids = response.body();
                    Log.d(TAG, "Covid cases: "+covids.getCases());
                    populateStats(covids);
                }
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }

    private void getUgandaCoronaStats(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<CoronaCountry> call = service.getOneCountry(UGANDA);
        call.enqueue(new Callback<CoronaCountry>() {
            @Override
            public void onResponse(Call<CoronaCountry> call, Response<CoronaCountry> response) {
                if (response.isSuccessful()){
                    CoronaCountry uganda = response.body();
                    if (uganda != null) {
                        ugandaStats(uganda);
                    }
                }
            }

            @Override
            public void onFailure(Call<CoronaCountry> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }

    private void fetchAllCountries(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<List<CoronaCountry>> call = service.getAllCountries();
        call.enqueue(new Callback<List<CoronaCountry>>() {
            @Override
            public void onResponse(Call<List<CoronaCountry>> call, Response<List<CoronaCountry>> response) {
                if (response.isSuccessful()) {
                    List<CoronaCountry> countryList = response.body();
                    Log.d(TAG, "Corona Countries: "+countryList.size());
                }
            }

            @Override
            public void onFailure(Call<List<CoronaCountry>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }
}
