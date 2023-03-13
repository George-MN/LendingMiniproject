package com.example.loanlending.Models;



import javax.persistence.*;

@Entity
@Table(name = "LoanProducts")
public class LoanProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanProductsId;
    private String loanProductName;
    private Integer tenureInDays;
    private double interestRate;
    private double maxLimit;

    public LoanProducts(Integer loanProductsId, String loanProductName, Integer tenureInDays, double interestRate, double maxLimit) {
        this.loanProductsId = loanProductsId;
        this.loanProductName = loanProductName;
        this.tenureInDays = tenureInDays;
        this.interestRate = interestRate;
        this.maxLimit = maxLimit;
    }

    public LoanProducts() {
    }

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public Integer getTenureInDays() {
        return tenureInDays;
    }

    public void setTenureInDays(Integer tenureInDays) {
        this.tenureInDays = tenureInDays;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Integer getLoanProductsId() {
        return loanProductsId;
    }

    public void setLoanProductsId(Integer loanProductsId) {
        this.loanProductsId = loanProductsId;
    }

    @Override
    public String toString() {
        return "LoanProducts{" +
                "loanProductsId=" + loanProductsId +
                ", loanProductName='" + loanProductName + '\'' +
                ", tenureInDays='" + tenureInDays + '\'' +
                ", interestRate=" + interestRate +
                ", maxLimit=" + maxLimit +
                '}';
    }
}
