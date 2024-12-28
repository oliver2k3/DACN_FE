package com.example.bankingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.database.dto.SavingDto;

import java.util.List;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.SavingViewHolder> {

    private List<SavingDto> savings;

    public SavingsAdapter(List<SavingDto> savings) {
        this.savings = savings;
    }

    @NonNull
    @Override
    public SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saving, parent, false);
        return new SavingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingViewHolder holder, int position) {
        SavingDto saving = savings.get(position);
        holder.savingAmount.setText(String.format("Amount: %s", saving.getAmount()));
        holder.savingDuration.setText(String.format("Duration: %d months", saving.getDepositDuration()));
    }

    @Override
    public int getItemCount() {
        return savings.size();
    }

    static class SavingViewHolder extends RecyclerView.ViewHolder {

        TextView savingAmount;
        TextView savingDuration;

        public SavingViewHolder(@NonNull View itemView) {
            super(itemView);
            savingAmount = itemView.findViewById(R.id.saving_amount);
            savingDuration = itemView.findViewById(R.id.saving_duration);
        }
    }
}