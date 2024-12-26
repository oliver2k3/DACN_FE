package com.example.bankingapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.dto.SavingDto;
import com.example.bankingapp.database.service.UserService;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingActivity extends AppCompatActivity {

    private UserStorage userStorage;
    private UserService userService;
    private EditText amountInput;
    private RadioGroup durationGroup;
    private TextView estimatedAmount;
    private TextView totalAmount;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        try {
            userStorage = new UserStorage(this);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        userService = Database.getClient().create(UserService.class);

        amountInput = findViewById(R.id.amount_input);
        durationGroup = findViewById(R.id.duration_group);
        estimatedAmount = findViewById(R.id.estimated_amount);
        totalAmount = findViewById(R.id.total_amount);
        submitButton = findViewById(R.id.submit_button);

        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateAmounts();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        durationGroup.setOnCheckedChangeListener((group, checkedId) -> updateAmounts());

        submitButton.setOnClickListener(v -> createSaving());
    }

    private void updateAmounts() {
        String amountStr = amountInput.getText().toString();
        if (amountStr.isEmpty()) {
            estimatedAmount.setText("Estimated Amount: ");
            totalAmount.setText("Total Amount: ");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int duration = 0;
        double interestRate = 0.0;

        int selectedId = durationGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.duration_6_months) {
            duration = 6;
            interestRate = 0.03;
        } else if (selectedId == R.id.duration_12_months) {
            duration = 12;
            interestRate = 0.05;
        } else if (selectedId == R.id.duration_24_months) {
            duration = 24;
            interestRate = 0.07;
        } else {
            estimatedAmount.setText("Estimated Amount: ");
            totalAmount.setText("Total Amount: ");
            return;
        }

        double estimatedInterest = amount * interestRate * duration / 12;
        double total = amount + estimatedInterest;

        estimatedAmount.setText("Estimated Amount: " + Math.round(estimatedInterest));
        totalAmount.setText("Total Amount: " + Math.round(total));
    }

    private void createSaving() {
        String amountStr = amountInput.getText().toString();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int duration = 0;
        double interestRate = 0.0;

        int selectedId = durationGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.duration_6_months) {
            duration = 6;
            interestRate = 0.03;
        } else if (selectedId == R.id.duration_12_months) {
            duration = 12;
            interestRate = 0.05;
        } else if (selectedId == R.id.duration_24_months) {
            duration = 24;
            interestRate = 0.07;
        } else {
            Toast.makeText(this, "Please select a deposit duration", Toast.LENGTH_SHORT).show();
            return;
        }

        SavingDto savingDto = new SavingDto();
        savingDto.setEmail(userStorage.getUser().getEmail());
        savingDto.setAmount(amount);
        savingDto.setDepositDuration(duration);
        savingDto.setInterestRate(interestRate);

        Call<Void> call = userService.createSaving(savingDto, "Bearer " + userStorage.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SavingActivity.this, "Saving created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SavingActivity.this, "Failed to create saving: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SavingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}