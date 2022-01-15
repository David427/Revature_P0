package com.revature.services;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.repositories.AccountRepo;
import com.revature.util.LinkedList;

public class AccountServiceImp implements AccountService {

    private AccountRepo accountRepo;

    public AccountServiceImp(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void addChecking(CheckingAccount c) {
        accountRepo.addChecking(c);
    }

    @Override
    public CheckingAccount getChecking(int id) {
        return accountRepo.getChecking(id);
    }

    @Override
    public CheckingAccount getCheckingByOwner(int id) {
        return accountRepo.getCheckingByOwner(id);
    }

    @Override
    public LinkedList<CheckingAccount> getAllChecking() {
        return accountRepo.getAllChecking();
    }

    @Override
    public void updateChecking(CheckingAccount change) {
        accountRepo.updateChecking(change);
    }

    @Override
    public void deleteChecking(int id) {
        accountRepo.deleteChecking(id);
    }

    @Override
    public void addSavings(SavingsAccount s) {
        accountRepo.addSavings(s);
    }

    @Override
    public SavingsAccount getSavings(int id) {
        return accountRepo.getSavings(id);
    }

    @Override
    public SavingsAccount getSavingsByOwner(int id) {
        return accountRepo.getSavingsByOwner(id);
    }

    @Override
    public LinkedList<SavingsAccount> getAllSavings() {
        return accountRepo.getAllSavings();
    }

    @Override
    public void updateSavings(SavingsAccount change) {
        accountRepo.updateSavings(change);
    }

    @Override
    public void deleteSavings(int id) {
        accountRepo.deleteSavings(id);
    }
}
