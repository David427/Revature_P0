package com.revature.repositories;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.Transaction;
import com.revature.util.LinkedList;

public interface AccountRepo {
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
}
