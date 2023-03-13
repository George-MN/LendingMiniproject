package com.example.loanlending.Controllers;

import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import com.example.loanlending.ResponseHandler.Response;
import com.example.loanlending.Services.UsersService;
import com.example.loanlending.Services.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    WalletService walletService;
    Logger logger= LoggerFactory.getLogger(UsersController.class);
    @PostMapping("/saveCustomer")
    public ResponseEntity<Object> saveUser(@RequestBody Users user){
        logger.info("Starting request");
        Users cust=usersService.saveUser(user);
        return Response.responseHelper("okay", HttpStatus.OK,cust,4001);
    }
    @GetMapping("/viewCustomers")
    public ResponseEntity<Object> getUser(){
        List<Users> customers= usersService.getAll();
        List<Wallet> wallets=walletService.getAllWallets();
        return Response.responseHelper("List Of Customers",HttpStatus.OK,wallets,4000);
    }
    @GetMapping("/getCutomerDetails/{customerID}")
    public ResponseEntity<Object> getUserById(@PathVariable("customerID") Integer userId){
        Users users=usersService.getUserById(userId);
        if(users==null){
            return Response.responseHelper("Customer does not exist",HttpStatus.BAD_REQUEST,users,4002);
        }
        Wallet wallet=walletService.getWalletByUser(users);
        return Response.responseHelper("Customer details",HttpStatus.OK,wallet,4000);
    }

}
