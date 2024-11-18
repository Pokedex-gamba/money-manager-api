package com.example.moneymanager.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_wallet")
public class UserWallet {

    @Id
    @Column(name = "user_id")
    private String userId;

    private int balance;

    public UserWallet(String userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public UserWallet() {}


    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
