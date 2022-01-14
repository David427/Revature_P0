package com.revature.repositories;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.util.LinkedList;

public interface AccountRepo {
    public CheckingAccount addChecking(CheckingAccount c);
    public CheckingAccount getChecking(int id);
    public LinkedList<CheckingAccount> getAllChecking();
    public CheckingAccount updateChecking(CheckingAccount change);
    public CheckingAccount deleteChecking(int id);

    public SavingsAccount addSavings(SavingsAccount s);
    public SavingsAccount getSavings(int id);
    public LinkedList<SavingsAccount> getAllSavings();
    public SavingsAccount updateSavings(SavingsAccount change);
    public SavingsAccount deleteSavings(int id);
}
