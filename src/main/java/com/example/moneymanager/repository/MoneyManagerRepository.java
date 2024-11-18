package com.example.moneymanager.repository;

import com.example.moneymanager.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface MoneyManagerRepository extends JpaRepository<UserWallet, UUID> {

    UserWallet findByUserId(String userId);

}
