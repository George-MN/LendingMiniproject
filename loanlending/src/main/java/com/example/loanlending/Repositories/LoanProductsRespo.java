package com.example.loanlending.Repositories;

import com.example.loanlending.Models.LoanProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanProductsRespo extends JpaRepository<LoanProducts,Integer> {
    List<LoanProducts> findLoanProductsByMaxLimitGreaterThanEqual(double amount);
    List<LoanProducts> findLoanProductsByMaxLimitLessThanEqual(double amount);
    LoanProducts findFirstByMaxLimitGreaterThanEqualOrderByMaxLimitAsc(double amount);
}
