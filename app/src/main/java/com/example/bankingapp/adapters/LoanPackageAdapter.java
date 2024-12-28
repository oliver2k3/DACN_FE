package com.example.bankingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bankingapp.R;
import com.example.bankingapp.database.models.GoiVay;
import java.util.List;

public class LoanPackageAdapter extends RecyclerView.Adapter<LoanPackageAdapter.LoanPackageViewHolder> {

    private final List<GoiVay> loanPackages;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GoiVay loanPackage);
    }

    public LoanPackageAdapter(List<GoiVay> loanPackages, OnItemClickListener listener) {
        this.loanPackages = loanPackages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LoanPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan_package, parent, false);
        return new LoanPackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanPackageViewHolder holder, int position) {
        GoiVay loanPackage = loanPackages.get(position);
        holder.bind(loanPackage, listener);
    }

    @Override
    public int getItemCount() {
        return loanPackages.size();
    }

    static class LoanPackageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameTextView;
        private final TextView interestRateTextView;
        private final TextView interestRate2TextView;
        private final TextView interestRate3TextView;

        public LoanPackageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.loan_package_image);
            nameTextView = itemView.findViewById(R.id.loan_package_name);
            interestRateTextView = itemView.findViewById(R.id.loan_package_interest_rate);
            interestRate2TextView = itemView.findViewById(R.id.loan_package_interest_rate2);
            interestRate3TextView = itemView.findViewById(R.id.loan_package_interest_rate3);
        }

        public void bind(final GoiVay loanPackage, final OnItemClickListener listener) {
            // Set image, name, and interest rates
            // imageView.setImageResource(loanPackage.getImage()); // Assuming image is a drawable resource ID
            nameTextView.setText(loanPackage.getTenGoiVay());
            interestRateTextView.setText("Interest Rate 1: " + loanPackage.getLaiSuatCoBan() + "%");
            interestRate2TextView.setText("Interest Rate 2: " + loanPackage.getLaiSuat2() + "%");
            interestRate3TextView.setText("Interest Rate 3: " + loanPackage.getLaiSuat3() + "%");

            itemView.setOnClickListener(v -> listener.onItemClick(loanPackage));
        }
    }
}