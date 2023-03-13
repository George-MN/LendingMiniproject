package com.example.loanlending.Controllers;

import com.example.loanlending.Models.LoanProducts;
import com.example.loanlending.Models.Loans;
import com.example.loanlending.Models.ResponseBody;
import com.example.loanlending.Models.Users;
import com.example.loanlending.Models.Wallet;
import com.example.loanlending.ResponseHandler.Response;
import com.example.loanlending.Services.LoansServices;
import com.example.loanlending.Services.UsersService;
import com.example.loanlending.Services.WalletService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("v1/loans")
public class LoanController {
    @Autowired
    private LoansServices loansServices;
    @Autowired
    private UsersService usersService;
    @Autowired
    private WalletService walletService;

    Logger logger=  LoggerFactory.getLogger(LoanController.class);
    @GetMapping("/getLoanOffers/{userId}")
    public ResponseEntity<Object> getLoanProduct(@PathVariable("userId") String userId){
        logger.info("Fetching Loan Offers");
        Users customer=usersService.getUserById(Integer.parseInt(userId));
        if(customer==null){
            return Response.responseHelper("Customer not found", HttpStatus.BAD_REQUEST,null,4002);
        }
        List<LoanProducts> loanOffers=loansServices.getLoanOffers(Integer.parseInt(userId));
        if(loanOffers==null){
            return Response.responseHelper("Customer does not qualify for loan", HttpStatus.OK,null,4003);

        }

        return Response.responseHelper("loan offers ", HttpStatus.OK,loanOffers,4000);

    }
    @PostMapping("/getLoan/{customerID}")
    public ResponseEntity<Object> getLoan(@PathVariable("customerID")Integer customerId,@RequestBody LoanProducts loan){
        Loans loans=loansServices.processLoan(customerId,loan);
        if(loans==null){
            return Response.responseHelper("Loan Failed",HttpStatus.OK,null,4002);
        }
        return Response.responseHelper("Loan Processed Successfully",HttpStatus.OK,loans,4000);


    }
    @GetMapping("/getActiveLoans/{customerID}")
    public ResponseEntity<Object> getCustomerActiveLoans(@PathVariable("customerID")Integer customerID){
        List<Loans> activeLoans=loansServices.getUserLoans(customerID);
          if(activeLoans==null){
              return Response.responseHelper("You don't have any Active loan",HttpStatus.OK,activeLoans,4002);
          }
          return Response.responseHelper("Active Loans",HttpStatus.OK,activeLoans,4000);

    }
    @PostMapping("/loanRepayment/{customerID}")
    public ResponseEntity<Object> loanPayment(@PathVariable("customerID")Integer customerID, @RequestBody Loans loan){
        boolean validate=loansServices.validateloan(loan,customerID);
        if(!validate){
            return Response.responseHelper("Wrong input",HttpStatus.BAD_REQUEST,null,4002);
        }
        Loans userLoan=loansServices.loanRepayment(loan.getLoanOfferID(),customerID);
        if(userLoan.getLoanStatus().equals("Active")){
            return Response.responseHelper("Could not process loan repayment at the moment",HttpStatus.INTERNAL_SERVER_ERROR,userLoan,4003);
        }
        return Response.responseHelper("Loan repaid successfully",HttpStatus.OK,userLoan,4000);

    }


}
