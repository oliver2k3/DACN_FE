package com.example.bankingapp.database.models;

import com.example.bankingapp.database.dto.UserDTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Transition implements Serializable {
    private int id;
    private UserDTO sender;
    private UserDTO receiver;
    private String message;
    private double amount;
    private long time;
    private double fee;

}
