package com.example.loanlending.Listener;

import com.example.loanlending.Models.Loans;
import com.example.loanlending.Repositories.LoanOffersRepo;
import com.example.loanlending.Services.LoansServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LoanRepaymentScheduler {
    @Autowired
    private LoanOffersRepo loanOffersRepo;
    @Autowired
    private LoansServices loansServices;

    Logger logger= LoggerFactory.getLogger(LoanRepaymentScheduler.class);
    @Scheduled(fixedDelay = 300000)
    public void loanRepaymentCron(){
        String today= LocalDate.now().toString();
        //Should be changed to query loans by due date: This is for testing purpose
        List<Loans> dueLoans=loanOffersRepo.findLoansByLoanStatus("Active");
        logger.info("Starting Processing loans due ->"+today+" size is "+dueLoans.size());
        for(Loans loan: dueLoans){
            logger.info("process Loan due date is-> "+loan.getDueDate());
            int userID=loan.getWallet().getUsers().getUserID();
            Loans status=loansServices.loanRepayment(loan.getLoanOfferID(),userID);
            logger.info("end process loan-> "+status);
        }


    }
}
