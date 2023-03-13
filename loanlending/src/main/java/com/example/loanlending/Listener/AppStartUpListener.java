package com.example.loanlending.Listener;

import com.example.loanlending.Models.LoanProducts;
import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import com.example.loanlending.Repositories.LoanProductsRespo;
import com.example.loanlending.Repositories.UsersRepo;
import com.example.loanlending.Repositories.WalletRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppStartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private LoanProductsRespo loanProductsRespo;
    Logger logger= LoggerFactory.getLogger(AppStartUpListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Loading 4 customers to the application");
        Users customer=new Users(10001,"John","Doe","johnDoe@lending.com");
        Users customer1=new Users(1002,"Jane","Doe","janedoe@lend.com");
        Users customer2=new Users(1003,"Richard","Roe","richardroe@lend.com");
        Users customer3=new Users(1003,"Joe","Citizen","joe@cit.co.ke");
        Users user=usersRepo.save(customer);
        Users user1=usersRepo.save(customer1);
        Users user2=usersRepo.save(customer2);
        Users user3=usersRepo.save(customer3);
       // Create Customer Wallets
        Wallet wallet=  new Wallet(2001,254_710_000_000l,2000.00,3000.00,"Active",user);
        Wallet wallet1= new Wallet(2002,254_710_000_001l,5000.00,500.00,"Active",user1);
        Wallet wallet2= new Wallet(2003,254_710_000_002l,200.00,1500.00,"Active",user2);
        Wallet wallet3= new Wallet(2004,254_710_000_003l,0.00,0.00,"Active",user3);

        Wallet w1=walletRepo.save(wallet);
        logger.info("1st Customer Details: "+w1);
        Wallet w2=walletRepo.save(wallet1);
        logger.info("2nd Customer details: "+w2);
        Wallet w3=walletRepo.save(wallet2);
        logger.info("3rd Customer details: "+w3);
        Wallet w4=walletRepo.save(wallet3);
        logger.info("4th Customer details: "+w4);
        LoanProducts basic=new LoanProducts(3001,"Basic",15,10.00,1000);
        LoanProducts standard= new LoanProducts(3002,"Standard",30,12.50,2500);
        LoanProducts l1=loanProductsRespo.save(basic);
        LoanProducts l2=loanProductsRespo.save(standard);
        logger.info("Basic product: "+l1);
        logger.info("standard product: "+l2);





    }
}
