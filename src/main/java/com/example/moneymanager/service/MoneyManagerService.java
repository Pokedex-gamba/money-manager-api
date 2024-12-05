package com.example.moneymanager.service;

import com.example.moneymanager.entity.UserWallet;
import com.example.moneymanager.repository.MoneyManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyManagerService {

    private MoneyManagerRepository moneyManagerRepository;

    public void saveMoney(UserWallet wallet){
        moneyManagerRepository.save(wallet);
    }

    public UserWallet findUsersWallet(String userId){
        return moneyManagerRepository.findByUserId(userId);
    }

    @Transactional
    public void addMoneyToAll(int amount) {
        moneyManagerRepository.findAll().forEach(user -> {
            user.setBalance(user.getBalance() + amount);
            moneyManagerRepository.save(user);
        });
    }

    @Autowired
    public void setMoneyManagerRepository(MoneyManagerRepository moneyManagerRepository) {
        this.moneyManagerRepository = moneyManagerRepository;
    }
}
