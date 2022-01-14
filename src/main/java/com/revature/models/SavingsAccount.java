package com.revature.models;

public class SavingsAccount {

    private int savingsId;
    private String savingsName;
    private double savingsBalance;

    public SavingsAccount() {
    }

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

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "savingsId=" + savingsId +
                ", savingsName='" + savingsName + '\'' +
                ", savingsBalance=" + savingsBalance +
                '}';
    }
}
