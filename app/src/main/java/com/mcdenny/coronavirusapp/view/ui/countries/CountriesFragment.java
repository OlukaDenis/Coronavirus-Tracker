package com.mcdenny.coronavirusapp.view.ui.countries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.model.CoronaCountry;
import com.mcdenny.coronavirusapp.view.adapters.CoronaCountryAdapter;

import java.util.List;

public class CountriesFragment extends Fragment {

    private CountriesViewModel countriesViewModel;
    private RecyclerView recyclerView;
    private CoronaCountryAdapter adapter;
    private List<CoronaCountry> countryList;
    private FirebaseAnalytics mFirebaseAnalytics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        CountriesViewModelFactory factory = new CountriesViewModelFactory(this.getActivity().getApplication());
        countriesViewModel = new ViewModelProvider(this, factory).get(CountriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_countries, container, false);

        //Init firebase analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(), this.getClass().getSimpleName(), this.getClass().getSimpleName());

        recyclerView = root.findViewById(R.id.country_recyclerview);

        countriesViewModel.getStatsByCountry().observe(this, coronaCountries -> {
            countryList = coronaCountries;
            showRecylyclerview();
        });
        return root;
    }

    private void showRecylyclerview() {
        adapter = new CoronaCountryAdapter(getContext(), countryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }
}