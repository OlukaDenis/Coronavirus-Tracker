package com.mcdenny.coronavirusapp.view.ui.faq;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mcdenny.coronavirusapp.view.ui.symptom_form.SymptomFormViewModel;

public class FaqViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;

    public FaqViewModelFactory(Application application) {
        mApplication = application;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> viewModel) {
        return (T) new FaqViewModel(mApplication);
    }
}
