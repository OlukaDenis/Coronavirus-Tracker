package com.premar.coronavirusapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.data.api.ApiClient;
import com.premar.coronavirusapp.data.api.ApiService;
import com.premar.coronavirusapp.model.CoronaCountry;
import com.premar.coronavirusapp.model.CoronaCountryResponse;
import com.premar.coronavirusapp.model.Covid;
import com.premar.coronavirusapp.viewmodel.CovidGlobalViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.premar.coronavirusapp.Utils.Constants.UGANDA;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
