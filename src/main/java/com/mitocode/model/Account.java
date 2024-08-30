package com.mitocode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountId;
    private double balance;

    public void updateBalance(double amount){
        this.balance += amount;
    }
}
