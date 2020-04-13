package com.mcdenny.coronavirusapp.view.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.data.api.ApiClient;
import com.mcdenny.coronavirusapp.data.api.ApiService;
import com.mcdenny.coronavirusapp.data.workers.CovidWorker;
import com.mcdenny.coronavirusapp.model.CoronaCountry;
import com.mcdenny.coronavirusapp.model.CountryInfo;
import com.mcdenny.coronavirusapp.model.Covid;
import com.mcdenny.coronavirusapp.view.ui.hospitals.HospitalViewModel;
import com.mcdenny.coronavirusapp.view.ui.hospitals.HospitalViewModelFactory;
import com.mcdenny.coronavirusapp.view.ui.symptom_form.SymptomFormActivity;
import com.mcdenny.coronavirusapp.view.ui.countries.CountriesFragment;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mcdenny.coronavirusapp.utils.Constants.NIGERIA;
import static com.mcdenny.coronavirusapp.utils.Constants.UPDATED;
import static com.mcdenny.coronavirusapp.utils.Constants.formatNumber;

public class HomeFragment extends Fragment {
    private WorkManager covidWorkManager;

    private TextView tvCases, tvDeaths, tvRecovered, ugCases, ugDeaths, ugRecovered, ugCasesToday, ugDeathsToday, moreCountries, moreFacts;
    private ImageView ugandaFlag;
    private Button btnSymptom, btnTest, btn_donate;
    private TextView countryName;
    private HomeViewModel viewModel;
    private static final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //view reference
        tvCases = root.findViewById(R.id.cases);
        tvDeaths = root.findViewById(R.id.deaths);
        tvRecovered = root.findViewById(R.id.recovered);
        ugCases = root.findViewById(R.id.uganda_cases);
        ugDeaths = root.findViewById(R.id.uganda_deaths);
        ugRecovered = root.findViewById(R.id.uganda_recovered);
        moreCountries = root.findViewById(R.id.more_countries);
        moreFacts = root.findViewById(R.id.more_facts);
        ugCasesToday = root.findViewById(R.id.uganda_cases_today);
        ugDeathsToday = root.findViewById(R.id.uganda_deaths_today);
        ugandaFlag = root.findViewById(R.id.uganda_flag);
        countryName = root.findViewById(R.id.country_name_status);
        btnSymptom = root.findViewById(R.id.btn_submit_info);
        btnTest = root.findViewById(R.id.btn_self_test);
        btn_donate = root.findViewById(R.id.btn_donate);

        btnSymptom.setOnClickListener(v -> startActivity(new Intent(getActivity(), SymptomFormActivity.class)) );
        moreCountries.setOnClickListener(v -> openCountryFragment());
        moreFacts.setOnClickListener(v -> openFactsFragment());
        btnTest.setOnClickListener(v -> openFaqFragment());
        btn_donate.setOnClickListener(v -> openDonateFragment());


        HomeViewModelFactory factory = new HomeViewModelFactory(this.getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        HospitalViewModelFactory mfactory = new HospitalViewModelFactory(this.getActivity().getApplication());
        HospitalViewModel viewModel = new ViewModelProvider(this, mfactory).get(HospitalViewModel.class);
        viewModel.getAllHospitals().observe(this, hospitals -> {
            Log.d(TAG, "Number of Hospitals: "+ hospitals.size());
        });

        // initializing a work manager
        covidWorkManager = WorkManager.getInstance(getActivity());

        Data source = new Data.Builder()
                .putString("workType", "PeriodicTime")
                .build();

        //periodic work request
        PeriodicWorkRequest periodicRequest = new PeriodicWorkRequest.Builder(CovidWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(source)
                .build();
        covidWorkManager.enqueue(periodicRequest);

        //work info
        covidWorkManager.getWorkInfoByIdLiveData(periodicRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();
            }
        });

        populateStats();
        getUgandaCoronaStats();

        return root;

    }

    private void openCountryFragment() {
        NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_countries);
    }

    private void openFaqFragment() {
        NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_faq);
    }

    private void openFactsFragment() {
        NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_facts);
    }

    private void openDonateFragment() {
        NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_donate);
    }


    private void populateStats() {
        Covid covid = viewModel.getGlobalStats(UPDATED);
        tvCases.setText(formatNumber(covid.getCases()));
        tvDeaths.setText(formatNumber(covid.getDeaths()));
        tvRecovered.setText(formatNumber(covid.getRecovered()));
    }

    private void countryStats(CoronaCountry country){
        CountryInfo countryInfo = country.getCountryInfo();
        Picasso.get()
                .load(countryInfo.getFlag())
                .placeholder(R.drawable.ic_flag)
                .error(R.drawable.ic_flag)
                .into(ugandaFlag);
        ugCases.setText(formatNumber(country.getCases()));
        ugDeaths.setText(formatNumber(country.getDeaths()));
        ugRecovered.setText(formatNumber(country.getRecovered()));

        Resources res = getResources();

        countryName.setText(String.format(res.getString(R.string.country_corona_status), country.getCountry()));

        ugCasesToday.setText(String.format(res.getString(R.string.today), country.getTodayCases()));
        ugDeathsToday.setText(String.format(res.getString(R.string.today), country.getTodayDeaths()));
    }

    private void getUgandaCoronaStats(){
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<CoronaCountry> call = service.getOneCountry(NIGERIA);
        call.enqueue(new Callback<CoronaCountry>() {
            @Override
            public void onResponse(Call<CoronaCountry> call, Response<CoronaCountry> response) {
                if (response.isSuccessful()){
                    CoronaCountry country = response.body();
                    if (country != null) {
                        countryStats(country);
                    }
                }
            }

            @Override
            public void onFailure(Call<CoronaCountry> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });

    }
}
