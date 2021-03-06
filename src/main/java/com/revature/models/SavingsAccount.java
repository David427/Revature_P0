package com.revature.models;

public class SavingsAccount {

    private int savingsId;
    private int ownerId;
    private String savingsName;
    private double savingsBalance;

    //region CONSTRUCTORS
    public SavingsAccount() {
    }

    public SavingsAccount(int ownerId, String savingsName) {
        this.ownerId = ownerId;
        this.savingsName = savingsName;
        this.savingsBalance = 0;
    }
    //endregion

    //region GETTERS & SETTERS
    public SavingsAccount(String savingsName, double savingsBalance) {
        this.savingsName = savingsName;
        this.savingsBalance = savingsBalance;
    }

    public int getSavingsId() {
        return savingsId;
    }

    public void setSavingsId(int savingsId) {
        this.savingsId = savingsId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getSavingsName() {
        return savingsName;
    }

    public void setSavingsName(String savingsName) {
        this.savingsName = savingsName;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }
    //endregion

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "savingsId=" + savingsId +
                ", savingsName='" + savingsName + '\'' +
                ", savingsBalance=" + savingsBalance +
                '}';
    }
}
