package com.example.loanlending.Repositories;

import com.example.loanlending.Models.Loans;
import com.example.loanlending.Models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanOffersRepo extends JpaRepository<Loans,Integer> {
    Loans findLoanOffersByLoanOfferID(int id);
    Loans getLoansByLoanOfferID(int id);
    List<Loans> findLoansByWalletAndLoanStatus(Wallet wallet,String status);

    List<Loans> findLoansByLoanStatus(String status);

    List<Loans> findAllByDueDateEquals(String dueDate);
    List<Loans> findByDueDateEquals(String dueDate);
    List<Loans> findAllByLoanStatusIs(String status);

}
