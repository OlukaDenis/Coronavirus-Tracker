package com.premar.coronavirusapp.data.api;

import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.CoronaCountryResponse;
import com.premar.coronavirusapp.model.Covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("all")
    Call<Covid> getWorldStats();

    @GET("countries")
    Call<List<CoronaCountry>> getAllCountries();

    @GET("countries/{country}")
    Call<CoronaCountry> getOneCountry(@Path("country") String country);
}
