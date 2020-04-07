package com.mcdenny.coronavirusapp.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcdenny.coronavirusapp.R;
import com.mcdenny.coronavirusapp.model.Hospital;
import com.mcdenny.coronavirusapp.view.interfaces.ItemClickListener;

public class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView hospitalName;
    private ItemClickListener itemClickListener;

    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);
        hospitalName = itemView.findViewById(R.id.tv_hospital);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void bindTO(Hospital hospital){
        hospitalName.setText(hospital.getName());
    }
}
