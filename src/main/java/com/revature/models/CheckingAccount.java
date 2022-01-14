package com.revature.models;

public class CheckingAccount {

    private int checkingId;
    private String checkingName;
    private double checkingBalance;

    public CheckingAccount() {
    }

    public CheckingAccount(int checkingId, String checkingName, double checkingBalance) {
        this.checkingId = checkingId;
        this.checkingName = checkingName;
        this.checkingBalance = checkingBalance;
    }

    public int getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(int checkingId) {
        this.checkingId = checkingId;
    }

    public String getCheckingName() {
        return checkingName;
    }

    public void setCheckingName(String checkingName) {
        this.checkingName = checkingName;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public void setCheckingBalance(double checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "checkingId=" + checkingId +
                ", checkingName='" + checkingName + '\'' +
                ", checkingBalance=" + checkingBalance +
                '}';
    }
}
