package com.example.loanlending.Services;

import com.example.loanlending.Models.Loans;
import com.example.loanlending.Models.Users;
import com.example.loanlending.Repositories.UsersRepo;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepo usersRepo;

    Logger logger= LoggerFactory.getLogger(UsersService.class);
    public Users saveUser(Users user){
        return usersRepo.save(user);
    }
    public Users getUserById(int userId){
        logger.debug("Getting user by ID: "+userId);
        return usersRepo.findUsersByUserID(userId);
    }
    public List<Users> getAll(){
        return usersRepo.findAll();
    }

}
