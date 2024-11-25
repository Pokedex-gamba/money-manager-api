package com.example.moneymanager.controller;

import com.example.moneymanager.entity.UserWallet;
import com.example.moneymanager.service.KeyLoaderService;
import com.example.moneymanager.service.MoneyManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MoneyManagerController {

    private MoneyManagerService moneyManagerService;

    private KeyLoaderService keyLoaderService;


    @GetMapping("/findUserWallet")
    public ResponseEntity<?> findUserWallet(@RequestHeader(HttpHeaders.AUTHORIZATION) String userToken) {
        String userId = getUserIdFromToken(userToken);
        UserWallet userWallet = moneyManagerService.findUsersWallet(userId);
        if (userWallet == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No user wallet found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok(userWallet);
        }
    }

    @GetMapping("/modifyBalance/{amount}")
    public ResponseEntity<?> addMoney(@RequestHeader(HttpHeaders.AUTHORIZATION) String userToken, @PathVariable int amount) {
        String userId = getUserIdFromToken(userToken);
        UserWallet userWallet = moneyManagerService.findUsersWallet(userId);
        if(userWallet == null) {
            UserWallet newWallet = new UserWallet(userId,amount);
            moneyManagerService.saveMoney(newWallet);
        } else {
            int value = userWallet.getBalance() + amount;
            userWallet.setBalance(value);
            moneyManagerService.saveMoney(userWallet);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private String getUserIdFromToken(String authHeader) {
        String token = authHeader.replace("Bearer", "").trim();

        PublicKey publicKey;
        try {
            String path = MoneyManagerController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File publicKeyFile = new File(path, "decoding_key");
            if (!publicKeyFile.exists()) {
                return "aaa";
            }
            BufferedReader reader = new BufferedReader(new FileReader(publicKeyFile));
            String publicKeyContent = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            publicKey = keyLoaderService.getPublicKey(publicKeyContent);
        } catch (Exception e) {
            return "bbb";
        }

        Claims claims = Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).getPayload();

        String userId = claims.get("user_id", String.class);
        if (userId == null) {
            return "ccc";
        }

        return userId;
    }

    @Autowired
    public void setMoneyManagerService(MoneyManagerService moneyManagerService) {
        this.moneyManagerService = moneyManagerService;
    }

    @Autowired
    public void setKeyLoaderService(KeyLoaderService keyLoaderService) {
        this.keyLoaderService = keyLoaderService;
    }
}
