package com.revature.services;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.Transaction;
import com.revature.util.LinkedList;

public interface AccountServiceTest {

    //Trivial methods.
    public void addChecking(CheckingAccount c);
    public CheckingAccount getChecking(int id);
    public CheckingAccount getCheckingByOwner(int id);
    public LinkedList<CheckingAccount> getAllChecking();
    public void updateChecking(CheckingAccount change);
    public void deleteChecking(int id);

    public void addSavings(SavingsAccount s);
    public SavingsAccount getSavings(int id);
    public SavingsAccount getSavingsByOwner(int id);
    public LinkedList<SavingsAccount> getAllSavings();
    public void updateSavings(SavingsAccount change);
    public void deleteSavings(int id);

    public void addTransaction(Transaction t);
    public LinkedList<Transaction> getCheckingTransactions(int id);
    public LinkedList<Transaction> getSavingsTransactions(int id);
    public LinkedList<Transaction> getAllTransactions(int id);

    //Complex methods.
    public void withdraw(CheckingAccount c, double balance);
    public void withdraw(SavingsAccount s, double balance);
    public void deposit(CheckingAccount c, double balance);
    public void deposit(SavingsAccount s, double balance);
    public void transfer(CheckingAccount c, SavingsAccount s, double cBalance, double sBalance);
    public void transfer(SavingsAccount s, CheckingAccount c, double sBalance, double cBalance);
    public void viewHistory(CheckingAccount c);
    public void viewHistory(SavingsAccount s);
    public void createChecking();
    public void createSavings();
}
