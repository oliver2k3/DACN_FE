package com.example.bankingapp.database.dto;

import lombok.Data;

@Data
public class SavingDto {
    private  String email;
    private double amount;
    private  int depositDuration;
    private double interestRate;

}
