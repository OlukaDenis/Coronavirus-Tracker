package com.mcdenny.coronavirusapp.view.ui.symptom_form;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.model.Form;
import com.mcdenny.coronavirusapp.model.Symptom;
import com.mcdenny.coronavirusapp.model.User;
import com.mcdenny.coronavirusapp.view.ui.HomeActivity;

import java.util.Objects;

public class SymptomFormActivity extends AppCompatActivity {
    private  String[] GENDER = new String[] {"Male", "Female", "Prefer not to say"};

    private TextInputEditText userName, userState, formHospital, userPhone;
    private AutoCompleteTextView userGender;

    private AlertDialog.Builder builder;

    private RadioGroup rgBreathing, rgTemperature, rgCough, rgChestPain;
    private RadioButton answerOne, answerTwo, answerThree, answerFour;
    private Button btnSubmitForm;

    private String breathAnswer, tempAnswer, coughAnswer, chestAnswer;
    private String name, state, hospital, phone;

    private  SymptomFormViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_form);

        builder = new AlertDialog.Builder(this);

        SymptomFormViewModelFactory factory = new SymptomFormViewModelFactory(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(SymptomFormViewModel.class);


        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.dropdown_pop_up_item, GENDER);

        userGender = findViewById(R.id.gender_exposed_dropdown);
        userGender.setKeyListener(null);
        userGender.setAdapter(adapter);

        userName = findViewById(R.id.user_name);
        userState = findViewById(R.id.state_name);
        formHospital = findViewById(R.id.hospital_name);
        userPhone = findViewById(R.id.phone_number);


        rgBreathing = findViewById(R.id.rg_breathing);
        rgChestPain = findViewById(R.id.rg_chest);
        rgCough = findViewById(R.id.rg_cough);
        rgTemperature = findViewById(R.id.rg_temperature);

        btnSubmitForm = findViewById(R.id.btn_submit_form);

        rgBreathing.setOnCheckedChangeListener((group, checkedId) -> {
            answerOne = findViewById(checkedId);
            breathAnswer = answerOne.getText().toString();
        });

        rgChestPain.setOnCheckedChangeListener((group, checkedId) -> {
            answerTwo = findViewById(checkedId);
            chestAnswer = answerTwo.getText().toString();
        });

        rgTemperature.setOnCheckedChangeListener((group, checkedId) -> {
            answerThree = findViewById(checkedId);
            tempAnswer = answerThree.getText().toString();
        });

        rgCough.setOnCheckedChangeListener((group, checkedId) -> {
            answerFour = findViewById(checkedId);
            coughAnswer = answerFour.getText().toString();
        });


        btnSubmitForm.setOnClickListener(v ->{
            sumbitForm();
        });
    }

    private void sumbitForm() {

        hospital = Objects.requireNonNull(formHospital.getText()).toString();

        //User
        name = Objects.requireNonNull(userName.getText()).toString();
        state = Objects.requireNonNull(userState.getText()).toString();
        phone = Objects.requireNonNull(userPhone.getText()).toString();
        String gender = userGender.getText().toString();
        User user = new User(name, state, gender, phone);

        //Symptoms
        Symptom symptom = new Symptom(breathAnswer, chestAnswer, tempAnswer, coughAnswer);

        if (rgBreathing.getCheckedRadioButtonId() == -1 || rgCough.getCheckedRadioButtonId() == -1 ||
            rgTemperature.getCheckedRadioButtonId() == -1 || rgChestPain.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please choose YES, NO or SOMETIMES", Toast.LENGTH_SHORT).show();
        } else if(name.isEmpty()){
            userName.setError("Please provide your name");
        }
        else if(state.isEmpty()){
            userState.setError("Please provide your State");
        }else if(phone.isEmpty()){
            userPhone.setError("Please provide your phone number");
        }else if(gender.isEmpty()){
            Toast.makeText(this, "Please you must provide your gender", Toast.LENGTH_SHORT).show();
        }else {
            //Form submission
            Form form = new Form(user, symptom, hospital);
            viewModel.sendFormData(form);

            builder.setTitle("Thank you for submitting your symptoms")
                    .setMessage("Our health staff will contact you soon")
                    .setPositiveButton("Okay", (dialog, which) -> {
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    });
            builder.setCancelable(false);
            builder.show();
        }

    }
}
