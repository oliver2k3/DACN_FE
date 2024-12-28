package com.example.bankingapp.database.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoiVay {
    private int maGoiVay;


    private String tenGoiVay;

    private float laiSuatCoBan;

    private float laiSuat2;

    private float laiSuat3;

    private String moTa1;

    private String moTa2;

    private String moTa3;

    private String moTa4;

    private String moTa5;

    private String moTa6;

    private String image;
}
