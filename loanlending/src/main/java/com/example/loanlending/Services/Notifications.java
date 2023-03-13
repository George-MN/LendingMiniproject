package com.example.loanlending.Services;

import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import com.example.loanlending.Repositories.UsersRepo;
import com.example.loanlending.Repositories.WalletRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Notifications {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private WalletRepo walletRepo;

    Logger logger= LoggerFactory.getLogger(Notifications.class);

    public Notifications() {
    }

    public boolean sendNotification(Users user, String message,long msisdn){
        String userEmail=user.getEmail();
        long phoneNumber=msisdn;
        String firstName=user.getFirstName();
        logger.info("Email sent to Customer:->\n Email address-> "+userEmail+"\n Message -> Dear "+firstName+" "+message);
        logger.info("SMS sent to cutomer ->\n Phone Number-> "+phoneNumber+"\n Message -> Dear "+firstName+" "+message);
        return true;

    }
}
