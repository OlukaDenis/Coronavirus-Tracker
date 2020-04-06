package com.mcdenny.coronavirusapp.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.model.Hospital;
import com.mcdenny.coronavirusapp.view.interfaces.ItemClickListener;
import com.mcdenny.coronavirusapp.view.viewholder.HospitalViewHolder;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mcdenny.coronavirusapp.Utils.Constants.HOSPITAL_RESULT_OK;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalViewHolder> {
    private Context context;
    private List<Hospital> hospitalList;

    public HospitalAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hospital, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        if (hospital != null ){
            holder.bindTO(hospital);
        }

        holder.setItemClickListener((view, i, isLongClicked) -> {
            Hospital selectedHospital = hospitalList.get(i);
            Intent intent = new Intent();
            intent.putExtra("hospital_name", selectedHospital.getName());
            ((Activity) context).setResult(RESULT_OK, intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList==null ? 0 : hospitalList.size();
    }
}
