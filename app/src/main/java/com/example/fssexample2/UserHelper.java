package com.example.fssexample2;

public class UserHelper {

    String name,wallet,amount,account,internetID,internetPass,mobile;

    public UserHelper() {
    }

    public UserHelper(String name, String wallet, String amount, String account, String internetID, String internetPass, String mobile) {
        this.name = name;
        this.wallet = wallet;
        this.amount = amount;
        this.account = account;
        this.internetID = internetID;
        this.internetPass = internetPass;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInternetID() {
        return internetID;
    }

    public void setInternetID(String internetID) {
        this.internetID = internetID;
    }

    public String getInternetPass() {
        return internetPass;
    }

    public void setInternetPass(String internetPass) {
        this.internetPass = internetPass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
