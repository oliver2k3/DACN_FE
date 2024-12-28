package com.example.bankingapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankingapp.R;
import com.example.bankingapp.adapters.SavingsAdapter;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.dto.SavingDto;
import com.example.bankingapp.database.service.UserService;
import com.example.bankingapp.storage.UserStorage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySavingsActivity extends AppCompatActivity {

    private UserStorage userStorage;
    private UserService userService;
    private RecyclerView savingsRecyclerView;
    private SavingsAdapter savingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_savings);

        try {
            userStorage = new UserStorage(this);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        userService = Database.getClient().create(UserService.class);

        savingsRecyclerView = findViewById(R.id.savings_recycler_view);
        savingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchSavings();
    }

    private void fetchSavings() {
        Call<List<SavingDto>> call = userService.getMySavings("Bearer " + userStorage.getToken());
        call.enqueue(new Callback<List<SavingDto>>() {
            @Override
            public void onResponse(Call<List<SavingDto>> call, Response<List<SavingDto>> response) {
                if (response.isSuccessful()) {
                    List<SavingDto> savings = response.body();
                    savingsAdapter = new SavingsAdapter(savings);
                    savingsRecyclerView.setAdapter(savingsAdapter);
                } else {
                    Toast.makeText(MySavingsActivity.this, "Failed to fetch savings: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SavingDto>> call, Throwable t) {
                Toast.makeText(MySavingsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}