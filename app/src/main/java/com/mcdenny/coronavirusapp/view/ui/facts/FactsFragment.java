package com.mcdenny.coronavirusapp.view.ui.facts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.view.ui.PreventionActivity;
import com.mcdenny.coronavirusapp.view.ui.SymptomsActivity;
import com.mcdenny.coronavirusapp.view.ui.TreatmentActivity;
import com.mcdenny.coronavirusapp.view.ui.countries.CountriesFragment;
import com.mcdenny.coronavirusapp.view.ui.faq.FaqFragment;
import com.mcdenny.coronavirusapp.view.ui.hospitals.HospitalActivity;

import java.util.Objects;

public class FactsFragment extends Fragment {
    private FactsViewModel factsViewModel;
    private CardView symptomCard, treatmentCard, faqCard, preventionCard;
    private FaqFragment faqFragment;
    private FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        factsViewModel =
                ViewModelProviders.of(this).get(FactsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_facts, container, false);

        symptomCard = root.findViewById(R.id.symptom_card);
        treatmentCard = root.findViewById(R.id.treatment_card);
        faqCard = root.findViewById(R.id.faq_card);
        preventionCard = root.findViewById(R.id.prevention_card);

        faqCard.setOnClickListener(v -> openTestFragment() );
        symptomCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), SymptomsActivity.class)));
        treatmentCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), TreatmentActivity.class)));
        preventionCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), PreventionActivity.class)));

        return root;
    }

    private void openTestFragment() {
        NavController navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_faq);
    }
}
