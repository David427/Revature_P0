package com.revature.services;

import com.revature.app.Main;
import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.Transaction;
import com.revature.repositories.AccountRepo;
import com.revature.util.LinkedList;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {

    private AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
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

    @Override
    public void addTransaction(Transaction t) {
        accountRepo.addTransaction(t);
    }

    @Override
    public LinkedList<Transaction> getCheckingTransactions(int id) {
        return accountRepo.getCheckingTransactions(id);
    }

    @Override
    public LinkedList<Transaction> getSavingsTransactions(int id) {
        return accountRepo.getSavingsTransactions(id);
    }

    @Override
    public LinkedList<Transaction> getAllTransactions( int id) {
        return accountRepo.getAllTransactions(id);
    }

    @Override
    public void withdraw(CheckingAccount c, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.checkingView = true;
        double withdrawal = 0;
        boolean validWithdrawal = false;
        boolean cancelWithdrawal = false;

        System.out.println("Enter an amount to withdraw in the following format: ##.##");
        while (!validWithdrawal && !cancelWithdrawal) {
            try {
                withdrawal = input.nextDouble();

                while (withdrawal > balance || withdrawal <= 0) {
                    System.out.println("ERROR: Invalid withdrawal amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        withdrawal = input.nextDouble();
                    } else if (option == 2) {
                        cancelWithdrawal = true;
                        withdrawal = 0;
                        break;
                    }
                }

                if (withdrawal <= balance && withdrawal != 0) {
                    validWithdrawal = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to withdraw" +
                        "in the following format: ##.##");
                input.next();
            }
        }
        c.setCheckingBalance(balance - withdrawal);
        updateChecking(c);

        if (validWithdrawal) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Withdrawal", "Checking", "N/A", withdrawal, currentTime);
            addTransaction(transaction);
            System.out.println("Withdrawal success: " + formatter.format(withdrawal));
        }

        Main.checkingBalance = c.getCheckingBalance();
        Main.checkingView = false;
    }

    @Override
    public void withdraw(SavingsAccount s, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.savingsView = true;
        double withdrawal = 0;
        boolean validWithdrawal = false;
        boolean cancelWithdrawal = false;

        System.out.println("Enter an amount to withdraw in the following format: ##.##");
        while (!validWithdrawal && !cancelWithdrawal) {
            try {
                withdrawal = input.nextDouble();

                while (withdrawal > balance || withdrawal <= 0) {
                    System.out.println("ERROR: Invalid withdrawal amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        withdrawal = input.nextDouble();
                    } else if (option == 2) {
                        cancelWithdrawal = true;
                        withdrawal = 0;
                        break;
                    }
                }

                if (withdrawal <= balance && withdrawal != 0) {
                    validWithdrawal = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to withdraw" +
                        "in the following format: ##.##");
                input.next();
            }
        }
        s.setSavingsBalance(balance - withdrawal);
        updateSavings(s);

        if (validWithdrawal) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Withdrawal", "Savings", "N/A", withdrawal, currentTime);
            addTransaction(transaction);
            System.out.println("Withdrawal success: " + formatter.format(withdrawal));
        }

        Main.savingsBalance = s.getSavingsBalance();
        Main.savingsView = false;
    }

    @Override
    public void deposit(CheckingAccount c, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.checkingView = true;
        double deposit = 0;
        boolean validDeposit = false;
        boolean cancelDeposit = false;

        System.out.println("Enter an amount to deposit in the following format: ##.##");
        while (!validDeposit && !cancelDeposit) {
            try {
                deposit = input.nextDouble();

                while (deposit <= 0) {
                    System.out.println("ERROR: Cannot deposit a negative or null amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        deposit = input.nextDouble();
                    } else if (option == 2) {
                        cancelDeposit = true;
                        deposit = 0;
                        break;
                    }
                }

                while (deposit > 10000) {
                    System.out.println("ERROR: Cannot deposit more than $10,000 at once.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        deposit = input.nextDouble();
                    } else if (option == 2) {
                        cancelDeposit = true;
                        deposit = 0;
                        break;
                    }
                }

                if (deposit > 0 && deposit <= 10000) {
                    validDeposit = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to deposit" +
                        "in the following format: ##.##");
                input.next();
            }
        }
        c.setCheckingBalance(balance + deposit);
        updateChecking(c);

        if (validDeposit) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Deposit", "N/A", "Checking", deposit, currentTime);
            addTransaction(transaction);
            System.out.println("Deposit success: " + formatter.format(deposit));
        }

        Main.checkingBalance = c.getCheckingBalance();
        Main.checkingView = false;
    }

    @Override
    public void deposit(SavingsAccount s, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.savingsView = true;
        double deposit = 0;
        boolean validDeposit = false;
        boolean cancelDeposit = false;

        System.out.println("Enter an amount to deposit in the following format: ##.##");
        while (!validDeposit && !cancelDeposit) {
            try {
                deposit = input.nextDouble();

                while (deposit <= 0) {
                    System.out.println("ERROR: Cannot deposit a negative or null amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        deposit = input.nextDouble();
                    } else if (option == 2) {
                        cancelDeposit = true;
                        deposit = 0;
                        break;
                    }
                }

                while (deposit > 10000) {
                    System.out.println("ERROR: Cannot deposit more than $10,000 at once.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount.");
                        deposit = input.nextDouble();
                    } else if (option == 2) {
                        cancelDeposit = true;
                        deposit = 0;
                        break;
                    }
                }

                if (deposit > 0 && deposit <= 10000) {
                    validDeposit = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to deposit" +
                        "in the following format: ##.##");
                input.next();
            }
        }
        s.setSavingsBalance(balance + deposit);
        updateSavings(s);

        if (validDeposit) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Deposit", "N/A", "Savings", deposit, currentTime);
            addTransaction(transaction);
            System.out.println("Deposit success: " + formatter.format(deposit));
        }

        Main.savingsBalance = s.getSavingsBalance();
        Main.savingsView = false;
    }

    @Override
    public void transfer(CheckingAccount c, SavingsAccount s, double cBalance, double sBalance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.checkingView = true;
        double transfer = 0;
        boolean validTransfer = false;
        boolean cancelTransfer = false;

        if (s.getSavingsId() == 0) {
            System.out.println("ERROR: You don't have a Savings account to transfer to! Please create one.");
            Main.accountsView = false;
            Main.checkingView = false;
            return;
        }

        System.out.println("Enter an amount to transfer in the following format: ##.##");
        while (!validTransfer && !cancelTransfer) {
            try {
                transfer = input.nextDouble();

                while (transfer > cBalance || transfer <= 0) {
                    System.out.println("ERROR: Invalid transfer amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount:");
                        transfer = input.nextDouble();
                    } else if (option == 2) {
                        cancelTransfer = true;
                        transfer = 0;
                        break;
                    }
                }

                if (transfer <= cBalance && transfer != 0) {
                    validTransfer = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to transfer" +
                        " in the following format: ##.##");
                input.next();
            }
        }
        c.setCheckingBalance(cBalance - transfer);
        s.setSavingsBalance(sBalance + transfer);
        updateChecking(c);
        updateSavings(s);

        if (validTransfer) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Transfer", "Checking", "Savings", transfer, currentTime);
            addTransaction(transaction);
            System.out.println("Transfer success: " + formatter.format(transfer));
        }

        Main.checkingBalance = c.getCheckingBalance();
        Main.checkingView = false;
    }

    @Override
    public void transfer(SavingsAccount s, CheckingAccount c, double sBalance, double cBalance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        Main.savingsView = true;
        double transfer = 0;
        boolean validTransfer = false;
        boolean cancelTransfer = false;

        if (c.getCheckingId() == 0) {
            System.out.println("ERROR: You don't have a Checking account to transfer to! Please create one.");
            Main.accountsView = false;
            Main.savingsView = false;
            return;
        }

        System.out.println("Enter an amount to transfer in the following format: ##.##");
        while (!validTransfer && !cancelTransfer) {
            try {
                transfer = input.nextDouble();

                while (transfer > sBalance || transfer <= 0) {
                    System.out.println("ERROR: Invalid transfer amount.");
                    option = Main.accountActionErrorMenu();

                    if (option == 1) {
                        System.out.println("Enter a new amount:");
                        transfer = input.nextDouble();
                    } else if (option == 2) {
                        cancelTransfer = true;
                        transfer = 0;
                        break;
                    }
                }

                if (transfer <= sBalance && transfer != 0) {
                    validTransfer = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Enter an amount to transfer" +
                        " in the following format: ##.##");
                input.next();
            }
        }
        s.setSavingsBalance(sBalance - transfer);
        c.setCheckingBalance(cBalance + transfer);
        updateSavings(s);
        updateChecking(c);

        if (validTransfer) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(Main.loggedInUserId, "Transfer", "Savings", "Checking", transfer, currentTime);
            addTransaction(transaction);
            System.out.println("Transfer success: " + formatter.format(transfer));
        }

        Main.savingsBalance = s.getSavingsBalance();
        Main.savingsView = false;
    }

    @Override
    public void viewHistory(CheckingAccount c) {
        Main.checkingView = true;
        System.out.println("===================" +
                "\nTRANSACTION HISTORY" +
                "\n===================");
        LinkedList<Transaction> cTransactions = getCheckingTransactions(Main.loggedInUserId);

        if (cTransactions.getSize() == 0) {
            System.out.println("ERROR: You have no transactions for this account.");
        } else {
            System.out.println(cTransactions);
        }
        Main.checkingView = false;
    }

    @Override
    public void viewHistory(SavingsAccount s) {
        Main.savingsView = true;
        System.out.println("===================" +
                "\nTRANSACTION HISTORY" +
                "\n===================");
        LinkedList<Transaction> sTransactions = getSavingsTransactions(Main.loggedInUserId);

        if (sTransactions.getSize() == 0) {
            System.out.println("ERROR: You have no transactions for this account.");
        } else {
            System.out.println(sTransactions);
        }
        Main.savingsView = false;
    }

    @Override
    public void createChecking() {
        Scanner input = new Scanner(System.in);
        CheckingAccount cAccount = getCheckingByOwner(Main.loggedInUserId);
        Main.creationView = true;

        if (cAccount.getCheckingId() != 0) {
            System.out.println("ERROR: You already have a Checking account!");
            Main.creationView = false;
            return;
        }

        System.out.println("Enter a friendly name for this account:");
        String newCheckingName = input.nextLine();

        CheckingAccount newChecking = new CheckingAccount(Main.loggedInUserId, newCheckingName);
        addChecking(newChecking);
        System.out.println("Account '" + newCheckingName + "' created!");
        Main.accountsView = false;
        Main.creationView = false;
    }

    @Override
    public void createSavings() {
        Scanner input = new Scanner(System.in);
        SavingsAccount sAccount = getSavingsByOwner(Main.loggedInUserId);
        Main.creationView = true;

        if (sAccount.getSavingsId() != 0) {
            System.out.println("ERROR: You already have a Savings account!");
            Main.creationView = false;
            return;
        }

        System.out.println("Enter a friendly name for this account:");
        String newSavingsName = input.nextLine();

        SavingsAccount newSavings = new SavingsAccount(Main.loggedInUserId, newSavingsName);
        addSavings(newSavings);
        System.out.println("Account '" + newSavingsName + "' created!");
        Main.accountsView = false;
        Main.creationView = false;
    }
}
