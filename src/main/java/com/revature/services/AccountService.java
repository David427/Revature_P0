package com.revature.services;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.util.LinkedList;

public interface AccountService {

    //Trivial methods.
    public void addChecking(CheckingAccount c);
    public CheckingAccount getChecking(int id);
    public LinkedList<CheckingAccount> getAllChecking();
    public void updateChecking(CheckingAccount change);
    public void deleteChecking(int id);

    public void addSavings(SavingsAccount s);
    public SavingsAccount getSavings(int id);
    public LinkedList<SavingsAccount> getAllSavings();
    public void updateSavings(SavingsAccount change);
    public void deleteSavings(int id);

    //Complex methods.

}
