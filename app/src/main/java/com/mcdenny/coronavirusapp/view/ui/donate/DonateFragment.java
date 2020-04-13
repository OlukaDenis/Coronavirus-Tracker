package com.mcdenny.coronavirusapp.view.ui.donate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mcdenny.coronavirusapp.R;

public class DonateFragment extends Fragment {

    private DonateViewModel donateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        donateViewModel =
                ViewModelProviders.of(this).get(DonateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_donate, container, false);

        return root;
    }
}