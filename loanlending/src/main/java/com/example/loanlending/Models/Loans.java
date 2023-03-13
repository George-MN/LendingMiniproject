package com.example.loanlending.Models;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Loan_Offers")
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer loanOfferID;
    private double amount;
    private String dueDate;
    private String loanStatus;
    private String offerDate;
    private double rate;
    @ManyToOne
    @JoinColumn(name="loanProductsId",referencedColumnName = "loanProductsId")
    private LoanProducts loanProducts;
    @ManyToOne
    @JoinColumn(name = "walletID",referencedColumnName = "walletID")
    private Wallet wallet;

    public Loans(double amount, String dueDate, String loanStatus, String offerDate, double rate, LoanProducts loanProducts, Wallet wallet) {
        this.amount = amount;
        this.dueDate = dueDate;
        this.loanStatus = loanStatus;
        this.offerDate = offerDate;
        this.rate = rate;
        this.loanProducts = loanProducts;
        this.wallet = wallet;
    }

    public Loans() {
    }

    public Integer getLoanOfferID() {
        return loanOfferID;
    }

    public void setLoanOfferID(Integer loanOfferID) {
        this.loanOfferID = loanOfferID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LoanProducts getLoanProducts() {
        return loanProducts;
    }

    public void setLoanProducts(LoanProducts loanProducts) {
        this.loanProducts = loanProducts;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    @Override
    public String toString() {
        return "Loans{" +
                "loanOfferID=" + loanOfferID +
                ", amount=" + amount +
                ", dueDate='" + dueDate + '\'' +
                ", loanStatus='" + loanStatus + '\'' +
                ", offerDate='" + offerDate + '\'' +
                ", rate=" + rate +
                ", loanProducts=" + loanProducts +
                ", wallet=" + wallet +
                '}';
    }
}
