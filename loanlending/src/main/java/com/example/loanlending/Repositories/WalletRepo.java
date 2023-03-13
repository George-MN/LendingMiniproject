package com.example.loanlending.Repositories;

import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Integer> {
    Wallet findWalletByUsers(Users users);
    Wallet getWalletByUsers(Users users);
    Wallet getWalletByWalletID(int id);
    List<Wallet> findAll();
}
