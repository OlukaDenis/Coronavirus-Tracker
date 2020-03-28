package com.premar.coronavirusapp.view.ui.facts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.premar.coronavirusapp.R;
import com.premar.coronavirusapp.view.ui.countries.CountriesViewModel;

public class FactsFragment extends Fragment {
    private FactsViewModel factsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        factsViewModel =
                ViewModelProviders.of(this).get(FactsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_facts, container, false);
        final TextView textView = root.findViewById(R.id.text_facts);
        factsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
