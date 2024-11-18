package com.example.moneymanager.service;

import com.example.moneymanager.entity.UserWallet;
import com.example.moneymanager.repository.MoneyManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MoneyManagerService {

    private MoneyManagerRepository moneyManagerRepository;

    public void saveMoney(UserWallet wallet){
        moneyManagerRepository.save(wallet);
    }

    public UserWallet findUsersWallet(String userId){
        return moneyManagerRepository.findByUserId(userId);
    }

    @Autowired
    public void setMoneyManagerRepository(MoneyManagerRepository moneyManagerRepository) {
        this.moneyManagerRepository = moneyManagerRepository;
    }
}
