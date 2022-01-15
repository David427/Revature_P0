package com.revature.models;

public class CheckingAccount {

    private int checkingId;
    private int ownerId;
    private String checkingName;
    private double checkingBalance;

    //region CONSTRUCTORS
    public CheckingAccount() {
    }

    public CheckingAccount(int ownerId, String checkingName) {
        this.ownerId = ownerId;
        this.checkingName = checkingName;
        this.checkingBalance = 0;
    }
    //endregion

    //region GETTERS & SETTERS
    public int getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(int checkingId) {
        this.checkingId = checkingId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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
    //endregion

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "checkingId=" + checkingId +
                ", checkingName='" + checkingName + '\'' +
                ", checkingBalance=" + checkingBalance +
                '}';
    }
}
