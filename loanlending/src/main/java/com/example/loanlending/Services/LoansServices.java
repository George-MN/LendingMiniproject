package com.example.loanlending.Services;

import com.example.loanlending.Models.*;
import com.example.loanlending.Repositories.LoanOffersRepo;
import com.example.loanlending.Repositories.LoanProductsRespo;
import com.example.loanlending.Repositories.UsersRepo;
import com.example.loanlending.Repositories.WalletRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LoansServices {
    @Autowired
    private LoanProductsRespo loanProductsRespo;
    @Autowired
    private LoanOffersRepo loanOffersRepo;
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private UsersRepo usersRepo;

    private Notifications notifications=new Notifications();

    Logger logger= LoggerFactory.getLogger(LoansServices.class);

    public List<LoanProducts> getLoanOffers(int userid){
        Users customer=usersRepo.findUsersByUserID(userid);
        Wallet userWallet= walletRepo.findWalletByUsers(customer);
        LoanProducts customLoan= new LoanProducts();
        double userLimit=userWallet.getLoanLimit();
        List<LoanProducts> loanProducts= loanProductsRespo.findLoanProductsByMaxLimitLessThanEqual(userLimit);

        // get details for customer with loan Limit higher than the last package in loanProducts
        LoanProducts lastProduct=loanProductsRespo.findFirstByMaxLimitGreaterThanEqualOrderByMaxLimitAsc(userLimit);
        if(lastProduct!=null){
            customLoan.setLoanProductName("Custom Loan");
            customLoan.setMaxLimit(userLimit);
            customLoan.setInterestRate(lastProduct.getInterestRate());
            customLoan.setTenureInDays(lastProduct.getTenureInDays());
            int prodId=loanProducts.size()==0?1:loanProducts.get(loanProducts.size()-1).getLoanProductsId()+1;
            customLoan.setLoanProductsId(prodId);
            loanProducts.add(customLoan);
        }
        return loanProducts;
    }
    public Loans processLoan(int userId,LoanProducts loanProduct){
        Users customer=usersRepo.findUsersByUserID(userId);
        logger.info("customer search status: "+customer);
        ResponseBody responseBody=new ResponseBody();
        List<LoanProducts>  loans= getLoanOffers(userId);
        boolean check=false;
        for(LoanProducts loanProducts1: loans){
            check=loanProducts1.getLoanProductName().equals(loanProduct.getLoanProductName())
                    && loanProducts1.getLoanProductsId()==loanProduct.getLoanProductsId()
                    && loanProducts1.getInterestRate()==loanProduct.getInterestRate()
                    && loanProducts1.getMaxLimit()==loanProduct.getMaxLimit()
                    && loanProducts1.getTenureInDays()==loanProduct.getTenureInDays();
            if(check==true){
                break;
            }

        }
        Wallet customerWallet=walletRepo.getWalletByUsers(customer);
        if(!check){
            Loans failedLoan=new Loans();
            logger.info("Loan status: "+ failedLoan);
            String message="Your loan worth "+loanProduct.getMaxLimit()+" could not be processed";
            notifications.sendNotification(customer,message,customerWallet.getMsisdn());
            return failedLoan;
        }
        //Wallet customerWallet=walletRepo.getWalletByUsers(customer);
        Loans loan= new Loans();
        logger.info("processing loan.....");
        double loanAmount=loanProduct.getMaxLimit();
        double currentCustomerBalance= customerWallet.getAccountBalance();
        double balanceAfter=loanAmount+currentCustomerBalance;
        double interest=(loanAmount*loanProduct.getInterestRate())/100.00;
        LocalDate currentDate=LocalDate.now();
        LocalDate dueDate=currentDate.plusDays(loanProduct.getTenureInDays());
        loan.setWallet(customerWallet);
        loan.setAmount(loanAmount+interest);
        loan.setLoanStatus("Active");
        loan.setRate(loanProduct.getInterestRate());
        loan.setOfferDate(currentDate.toString());
        loan.setDueDate(dueDate.toString());
        Loans activeLoan=loanOffersRepo.save(loan);
        customerWallet.setAccountBalance(balanceAfter);
        walletRepo.save(customerWallet);
        responseBody.setResponseCode(4000);
        responseBody.setresponseDescription("You have SuccessFully been granted a loan");
        logger.info("The Below Loan Has Been Processed: "+activeLoan);
        String message="You have SuccessFully been granted a loan worth "+loanAmount;
        notifications.sendNotification(customer,message,customerWallet.getMsisdn());
        return activeLoan;


    }
    public ResponseBody loanPaymentv1(Loans loan){
        ResponseBody responseBody= new ResponseBody();
        Loans activeLoan=loanOffersRepo.getLoansByLoanOfferID(loan.getLoanOfferID());
        if(activeLoan==null || !activeLoan.getLoanStatus().equals("Active")){
            responseBody.setResponseCode(4003);
            responseBody.setresponseDescription("The Loan does not exist");
            return responseBody;
        }
        Wallet customerWallet=walletRepo.getWalletByWalletID(activeLoan.getWallet().getWalletID());
        double loanAmount= activeLoan.getAmount();
        double customerBalance=customerWallet.getAccountBalance();
        if(loanAmount>customerBalance){
            responseBody.setResponseCode(4003);
            responseBody.setresponseDescription("Insufficient balance to repay the loan");
            return responseBody;
        }
        Double customerBalanceAfter=customerBalance-loanAmount;
        double loanAmountAfter=0.00;
        activeLoan.setLoanStatus("Paid");
        activeLoan.setAmount(loanAmountAfter);
        customerWallet.setAccountBalance(customerBalanceAfter);
        loanOffersRepo.save(activeLoan);
        walletRepo.save(customerWallet);
        responseBody.setResponseCode(4000);
        responseBody.setresponseDescription("You have settled your loan successfully");
        return responseBody;

    }
    public List<Loans> getUserLoans(int userId){
        Users user=usersRepo.findUsersByUserID(userId);
        Wallet userWallet=walletRepo.getWalletByUsers(user);
        List<Loans> customerLoans=loanOffersRepo.findLoansByWalletAndLoanStatus(userWallet,"Active");
        return customerLoans;
    }
    public Loans loanRepayment(Integer loans,int customerID){
        Loans loan=loanOffersRepo.findLoanOffersByLoanOfferID(loans);
        Wallet customerWallet=loan.getWallet();
        Users customer=customerWallet.getUsers();
        double customerBalance=customerWallet.getAccountBalance();
        double loanAmount=loan.getAmount();
        double customerBalanceAfter=customerBalance-loanAmount;
        if(customerBalanceAfter<0){
            String message="Your balance is insufficient to repay the loan worth "+loanAmount;
            notifications.sendNotification(customer,message,customerWallet.getMsisdn());
            return loan;
        }
        loan.setAmount(0.00);
        loan.setLoanStatus("Paid");
        customerWallet.setAccountBalance(customerBalanceAfter);
        walletRepo.save(customerWallet);
        Loans paidLoan=loanOffersRepo.save(loan);
        String message="Your have successfully paid the loan worth "+loanAmount;
        notifications.sendNotification(customer,message,customerWallet.getMsisdn());
        return paidLoan;



    }
    public boolean validateloan(Loans loan,int UserId){
        if(loan==null){
            return false;
        }
        Users user=usersRepo.findUsersByUserID(UserId);
        Loans userLoan=loanOffersRepo.getLoansByLoanOfferID(loan.getLoanOfferID());
        if(user==null || userLoan==null){
            return false;
        }
        boolean check=loan.getAmount()==userLoan.getAmount()
                && loan.getOfferDate().equals(userLoan.getOfferDate())
                && loan.getDueDate().equals(userLoan.getDueDate())
                && loan.getRate()==userLoan.getRate();
        return check;
    }

}
