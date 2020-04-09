package com.mcdenny.coronavirusapp.view.ui.faq;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcdenny.coronavirusapp.data.repository.CovidRepository;
import com.mcdenny.coronavirusapp.model.Form;
import com.mcdenny.coronavirusapp.model.Test;

public class FaqViewModel extends ViewModel {

    private CovidRepository repository;

    public FaqViewModel(Application application) {
       repository = new CovidRepository(application);
    }

    public void saveTests(Test test){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formReference = database.getReference().child("self-test");

        formReference.push().setValue(test);
    }
}