package com.example.bankingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bankingapp.R;
import com.example.bankingapp.activities.Exchange;
import com.example.bankingapp.activities.MySavingsActivity;
import com.example.bankingapp.activities.PayBill;
import com.example.bankingapp.activities.PaymentHistory;
import com.example.bankingapp.activities.SavingActivity;
import com.example.bankingapp.activities.TransactionHistory;
import com.example.bankingapp.activities.Transfer;
import com.example.bankingapp.database.Database;
import com.example.bankingapp.database.dto.SavingDto;
import com.example.bankingapp.database.dto.UserDTO;
import com.example.bankingapp.database.service.UserService;
import com.example.bankingapp.storage.UserStorage;
import com.example.bankingapp.utils.BalanceDisplay;
import com.example.bankingapp.utils.CardDisplay;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private UserStorage userStorage;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private CardView transfer, report, pay_bill, exchange, payment_report, saving,mysavings;
    private List<CardView> listCard;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            userStorage = new UserStorage(requireContext());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        String token = userStorage.getToken();



        // Or log the token for debugging purposes
        Log.d("UserToken", "User Token: " + token);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        transfer = view.findViewById(R.id.transfer);
        report = view.findViewById(R.id.report);
        payment_report = view.findViewById(R.id.payment_report);
        pay_bill = view.findViewById(R.id.pay_bill);
        exchange = view.findViewById(R.id.exchange);
        saving = view.findViewById(R.id.saving);
        mysavings = view.findViewById(R.id.my_savings);
        TextView textView = view.findViewById(R.id.textView3);
        TextView card_name = view.findViewById(R.id.card_name);
        TextView card_number = view.findViewById(R.id.card_number);
        TextView balance = view.findViewById(R.id.balance);

        UserService userService = Database.getClient().create(UserService.class);
        Call<UserDTO> call = userService.getCurrentUser("Bearer " + userStorage.getToken());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    double bal = response.body().getBalance();
                    balance.setText(BalanceDisplay.builder().balance(bal).build().display());
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Handle failure
            }
        });

        textView.setText(String.format("%s, %s", getString(R.string.welcome), userStorage.getUser().getName()));
        card_name.setText(userStorage.getUser().getName());
        card_number.setText(CardDisplay.builder().cardNumber(userStorage.getUser().getCardNumber()).build().displayShow());

        listCard = new ArrayList<>(Arrays.asList(transfer, report, pay_bill, exchange, payment_report, saving));

        listCard.forEach(card -> {
            card.setOnClickListener(v -> {
                if (v.getId() == transfer.getId()) {
                    Intent intent = new Intent(getActivity(), Transfer.class);
                    startActivity(intent);
                } else if (v.getId() == report.getId()) {
                    Intent intent = new Intent(getActivity(), TransactionHistory.class);
                    startActivity(intent);
                } else if (v.getId() == payment_report.getId()) {
                    Intent intent = new Intent(getActivity(), PaymentHistory.class);
                    startActivity(intent);
                } else if (v.getId() == pay_bill.getId()) {
                    Intent intent = new Intent(getActivity(), PayBill.class);
                    startActivity(intent);
                } else if (v.getId() == exchange.getId()) {
                    Intent intent = new Intent(getActivity(), Exchange.class);
                    startActivity(intent);
                } else if (v.getId() == saving.getId()) {
                    Intent intent = new Intent(getActivity(), SavingActivity.class);
                    startActivity(intent);
                }else if (v.getId() == mysavings.getId()) {
                    Intent intent = new Intent(getActivity(), MySavingsActivity.class);
                    startActivity(intent);
                }
            });
        });
 
        return view;
    }

}