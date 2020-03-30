package com.premar.coronavirusapp.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.premar.coronavirusapp.R;

import static java.security.AccessController.getContext;

public class SymptomFormActivity extends AppCompatActivity {
    private  String[] GENDER = new String[] {"Male", "Female", "Prefer not to say"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_form);


        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.dropdown_pop_up_item, GENDER);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.gender_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
    }
}
