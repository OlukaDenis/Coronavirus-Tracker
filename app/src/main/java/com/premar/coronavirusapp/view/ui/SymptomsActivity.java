package com.premar.coronavirusapp.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.premar.coronavirusapp.R;

public class SymptomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        setTitle(R.string.corona_symptoms);
    }
}
