package com.example.loanlending.Models;

import javax.persistence.*;

@Entity
@Table(name = "Wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletID;
    @Column(unique = true)
    private long msisdn;
    private double accountBalance;
    private double loanLimit;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId",referencedColumnName = "userID")
    private Users users;


    public Wallet(Integer walletID, long msisdn, double accountBalance, double loanLimit, String status, Users users) {
        this.walletID = walletID;
        this.msisdn = msisdn;
        this.accountBalance = accountBalance;
        this.loanLimit = loanLimit;
        this.status = status;
        this.users = users;
    }

    public Wallet() {
    }

    public long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(long msisdn) {
        this.msisdn = msisdn;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(double loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getWalletID() {
        return walletID;
    }

    public void setWalletID(Integer walletID) {
        this.walletID = walletID;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletID=" + walletID +
                ", msisdn=" + msisdn +
                ", accountBalance=" + accountBalance +
                ", loanLimit=" + loanLimit +
                ", status='" + status + '\'' +
                ", users=" + users +
                '}';
    }
}
