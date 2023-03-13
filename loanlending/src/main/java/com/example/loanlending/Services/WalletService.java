package com.example.loanlending.Services;

import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import com.example.loanlending.Repositories.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    @Autowired
    WalletRepo walletRepo;
    public List<Wallet> getAllWallets(){
        return walletRepo.findAll();
    }
    public Wallet getWalletByUser(Users users){
        return walletRepo.getWalletByUsers(users);
    }
}
