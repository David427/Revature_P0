package com.revature.services;

import com.revature.models.Transaction;
import com.revature.repositories.AccountRepoTest;
import com.revature.app.TestDriver;
import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.util.LinkedList;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountServiceImplTest implements AccountServiceTest {

    private AccountRepoTest accountRepoTest;

    public AccountServiceImplTest(AccountRepoTest accountRepoTest) {
        this.accountRepoTest = accountRepoTest;
    }

    @Override
    public void addChecking(CheckingAccount c) {
        accountRepoTest.addChecking(c);
    }

    @Override
    public CheckingAccount getChecking(int id) {
        return accountRepoTest.getChecking(id);
    }

    @Override
    public CheckingAccount getCheckingByOwner(int id) {
        return accountRepoTest.getCheckingByOwner(id);
    }

    @Override
    public LinkedList<CheckingAccount> getAllChecking() {
        return accountRepoTest.getAllChecking();
    }

    @Override
    public void updateChecking(CheckingAccount change) {
        accountRepoTest.updateChecking(change);
    }

    @Override
    public void deleteChecking(int id) {
        accountRepoTest.deleteChecking(id);
    }

    @Override
    public void addSavings(SavingsAccount s) {
        accountRepoTest.addSavings(s);
    }

    @Override
    public SavingsAccount getSavings(int id) {
        return accountRepoTest.getSavings(id);
    }

    @Override
    public SavingsAccount getSavingsByOwner(int id) {
        return accountRepoTest.getSavingsByOwner(id);
    }

    @Override
    public LinkedList<SavingsAccount> getAllSavings() {
        return accountRepoTest.getAllSavings();
    }

    @Override
    public void updateSavings(SavingsAccount change) {
        accountRepoTest.updateSavings(change);
    }

    @Override
    public void deleteSavings(int id) {
        accountRepoTest.deleteSavings(id);
    }

    @Override
    public void addTransaction(Transaction t) {
        accountRepoTest.addTransaction(t);
    }

    @Override
    public LinkedList<Transaction> getCheckingTransactions(int id) {
        return accountRepoTest.getCheckingTransactions(id);
    }

    @Override
    public LinkedList<Transaction> getSavingsTransactions(int id) {
        return accountRepoTest.getSavingsTransactions(id);
    }

    @Override
    public LinkedList<Transaction> getAllTransactions(int id) {
        return accountRepoTest.getAllTransactions(id);
    }

    @Override
    public void withdraw(CheckingAccount c, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.checkingView = true;
        double withdrawal = 0;
        boolean validWithdrawal = false;
        boolean cancelWithdrawal = false;

        System.out.println("Enter an amount to withdraw in the following format: ##.##");
        while (!validWithdrawal && !cancelWithdrawal) {
            try {
                withdrawal = input.nextDouble();

                while (withdrawal > balance || withdrawal <= 0) {
                    System.out.println("ERROR: Invalid withdrawal amount.");
                    option = TestDriver.accountActionErrorMenu();

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
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Withdrawal", "Checking", "N/A", withdrawal, currentTime);
            addTransaction(transaction);
            System.out.println("Withdrawal success: " + formatter.format(withdrawal));
        }

        TestDriver.checkingBalance = c.getCheckingBalance();
        TestDriver.checkingView = false;
    }

    @Override
    public void withdraw(SavingsAccount s, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.savingsView = true;
        double withdrawal = 0;
        boolean validWithdrawal = false;
        boolean cancelWithdrawal = false;

        System.out.println("Enter an amount to withdraw in the following format: ##.##");
        while (!validWithdrawal && !cancelWithdrawal) {
            try {
                withdrawal = input.nextDouble();

                while (withdrawal > balance || withdrawal <= 0) {
                    System.out.println("ERROR: Invalid withdrawal amount.");
                    option = TestDriver.accountActionErrorMenu();

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
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Withdrawal", "Savings", "N/A", withdrawal, currentTime);
            addTransaction(transaction);
            System.out.println("Withdrawal success: " + formatter.format(withdrawal));
        }

        TestDriver.savingsBalance = s.getSavingsBalance();
        TestDriver.savingsView = false;
    }

    @Override
    public void deposit(CheckingAccount c, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.checkingView = true;
        double deposit = 0;
        boolean validDeposit = false;
        boolean cancelDeposit = false;

        System.out.println("Enter an amount to deposit in the following format: ##.##");
        while (!validDeposit && !cancelDeposit) {
            try {
                deposit = input.nextDouble();

                while (deposit <= 0) {
                    System.out.println("ERROR: Cannot deposit a negative or null amount.");
                    option = TestDriver.accountActionErrorMenu();

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
                    option = TestDriver.accountActionErrorMenu();

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
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Deposit", "N/A", "Checking", deposit, currentTime);
            addTransaction(transaction);
            System.out.println("Deposit success: " + formatter.format(deposit));
        }

        TestDriver.checkingBalance = c.getCheckingBalance();
        TestDriver.checkingView = false;
    }

    @Override
    public void deposit(SavingsAccount s, double balance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.savingsView = true;
        double deposit = 0;
        boolean validDeposit = false;
        boolean cancelDeposit = false;

        System.out.println("Enter an amount to deposit in the following format: ##.##");
        while (!validDeposit && !cancelDeposit) {
            try {
                deposit = input.nextDouble();

                while (deposit <= 0) {
                    System.out.println("ERROR: Cannot deposit a negative or null amount.");
                    option = TestDriver.accountActionErrorMenu();

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
                    option = TestDriver.accountActionErrorMenu();

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
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Deposit", "N/A", "Savings", deposit, currentTime);
            addTransaction(transaction);
            System.out.println("Deposit success: " + formatter.format(deposit));
        }

        TestDriver.savingsBalance = s.getSavingsBalance();
        TestDriver.savingsView = false;
    }

    @Override
    public void transfer(CheckingAccount c, SavingsAccount s, double cBalance, double sBalance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.checkingView = true;
        double transfer = 0;
        boolean validTransfer = false;
        boolean cancelTransfer = false;

        if (s.getSavingsId() == 0) {
            System.out.println("ERROR: You don't have a Savings account to transfer to! Please create one.");
            TestDriver.accountsView = false;
            return;
        }

        System.out.println("Enter an amount to transfer in the following format: ##.##");
        while (!validTransfer && !cancelTransfer) {
            try {
                transfer = input.nextDouble();

                while (transfer > cBalance || transfer <= 0) {
                    System.out.println("ERROR: Invalid transfer amount.");
                    option = TestDriver.accountActionErrorMenu();

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
                        "in the following format: ##.##");
                input.next();
            }
        }
        c.setCheckingBalance(cBalance - transfer);
        s.setSavingsBalance(sBalance + transfer);
        updateChecking(c);
        updateSavings(s);

        if (validTransfer) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Transfer", "Checking", "Savings", transfer, currentTime);
            addTransaction(transaction);
            System.out.println("Transfer success: " + formatter.format(transfer));
        }

        TestDriver.checkingBalance = c.getCheckingBalance();
        TestDriver.checkingView = false;
    }

    @Override
    public void transfer(SavingsAccount s, CheckingAccount c, double sBalance, double cBalance) {
        Scanner input = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int option;
        TestDriver.savingsView = true;
        double transfer = 0;
        boolean validTransfer = false;
        boolean cancelTransfer = false;

        if (c.getCheckingId() == 0) {
            System.out.println("ERROR: You don't have a Checking account to transfer to! Please create one.");
            TestDriver.accountsView = false;
            return;
        }

        System.out.println("Enter an amount to transfer in the following format: ##.##");
        while (!validTransfer && !cancelTransfer) {
            try {
                transfer = input.nextDouble();

                while (transfer > cBalance || transfer <= 0) {
                    System.out.println("ERROR: Invalid transfer amount.");
                    option = TestDriver.accountActionErrorMenu();

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
                        "in the following format: ##.##");
                input.next();
            }
        }
        s.setSavingsBalance(sBalance - transfer);
        c.setCheckingBalance(cBalance + transfer);
        updateSavings(s);
        updateChecking(c);

        if (validTransfer) {
            long currentTime = System.currentTimeMillis();
            Transaction transaction = new Transaction(TestDriver.loggedInUserId, "Transfer", "Savings", "Checking", transfer, currentTime);
            addTransaction(transaction);
            System.out.println("Transfer success: " + formatter.format(transfer));
        }

        TestDriver.savingsBalance = s.getSavingsBalance();
        TestDriver.savingsView = false;
    }

    @Override
    public void viewHistory(CheckingAccount c) {
        TestDriver.checkingView = true;
        System.out.println("===================" +
                "\nTRANSACTION HISTORY" +
                "\n===================");
        LinkedList<Transaction> cTransactions = getCheckingTransactions(TestDriver.loggedInUserId);

        if (cTransactions.getSize() == 0) {
            System.out.println("ERROR: You have no transactions for this account.");
        } else {
            System.out.println(cTransactions);
        }
        TestDriver.checkingView = false;
    }

    @Override
    public void viewHistory(SavingsAccount s) {
        TestDriver.savingsView = true;
        System.out.println("===================" +
                "\nTRANSACTION HISTORY" +
                "\n===================");
        LinkedList<Transaction> sTransactions = getSavingsTransactions(TestDriver.loggedInUserId);

        if (sTransactions.getSize() == 0) {
            System.out.println("ERROR: You have no transactions for this account.");
        } else {
            System.out.println(sTransactions);
        }
        TestDriver.savingsView = false;
    }

    @Override
    public void createChecking() {
        Scanner input = new Scanner(System.in);
        CheckingAccount cAccount = getCheckingByOwner(TestDriver.loggedInUserId);
        TestDriver.creationView = true;

        if (cAccount.getCheckingId() != 0) {
            System.out.println("ERROR: You already have a Checking account!");
            TestDriver.creationView = false;
            return;
        }

        System.out.println("Enter a friendly name for this account:");
        String newCheckingName = input.nextLine();

        CheckingAccount newChecking = new CheckingAccount(TestDriver.loggedInUserId, newCheckingName);
        addChecking(newChecking);
        System.out.println("Account '" + newCheckingName + "' created!");
        TestDriver.accountsView = false;
    }

    @Override
    public void createSavings() {
        Scanner input = new Scanner(System.in);
        SavingsAccount sAccount = getSavingsByOwner(TestDriver.loggedInUserId);
        TestDriver.creationView = true;

        if (sAccount.getSavingsId() != 0) {
            System.out.println("ERROR: You already have a Savings account!");
            TestDriver.creationView = false;
            return;
        }

        System.out.println("Enter a friendly name for this account:");
        String newSavingsName = input.nextLine();

        SavingsAccount newSavings = new SavingsAccount(TestDriver.loggedInUserId, newSavingsName);
        addSavings(newSavings);
        System.out.println("Account '" + newSavingsName + "' created!");
        TestDriver.accountsView = false;
    }
}
