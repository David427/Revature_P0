package com.revature.app;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.repositories.AccountRepoImpl;
import com.revature.repositories.UserRepoImpl;
import com.revature.services.AccountServiceImpl;
import com.revature.services.UserServiceImpl;

import java.text.NumberFormat;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    //region DECLARE & INIT VARS
    static Scanner input = new Scanner(System.in);
    static UserRepoImpl userRepo = new UserRepoImpl();
    static UserServiceImpl userService = new UserServiceImpl(userRepo);
    static AccountRepoImpl accountRepo = new AccountRepoImpl();
    static AccountServiceImpl accountService = new AccountServiceImpl(accountRepo);
    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    public static boolean loggedIn = false;
    public static int loggedInUserId = 0;
    public static boolean accountsView = false;
    public static boolean checkingView = false;
    public static boolean savingsView = false;
    public static boolean creationView = false;
    static String checkingName;
    static String savingsName;
    public static double checkingBalance = 0;
    public static double savingsBalance = 0;
    //endregion

    public static void main(String[] args) {
        int option;

        while (!loggedIn) {
            option = loginMenu();

            //User actions.
            switch (option) {
                case 1: userService.logIn();
                        break;
                case 2: userService.register();
                        break;
                case 3: System.out.println("Thank you for visiting David's Bank. Goodbye.");
                        exit(0);
            }

            //Main menu actions.
            while (loggedIn && !accountsView) {
                option = mainMenu();

                if (option == 1) { //View Checking account.
                    accountsView = true;

                    //Retrieve Checking & Savings accounts belonging to the currently logged in user.
                    CheckingAccount cAccount = accountService.getCheckingByOwner(loggedInUserId);
                    SavingsAccount sAccount = accountService.getSavingsByOwner(loggedInUserId);

                    if (cAccount.getCheckingId() == 0) {
                        System.out.println("ERROR: You don't have a Checking account! Please create one.");
                        accountsView = false;
                        continue;
                    }

                    //Retrieve account balances and friendly Checking account name.
                    checkingBalance = cAccount.getCheckingBalance();
                    savingsBalance = sAccount.getSavingsBalance();
                    checkingName = cAccount.getCheckingName();

                    while (loggedIn && accountsView && !checkingView) {
                        option = checkingMenu();
                        checkingBalance = cAccount.getCheckingBalance();

                        switch (option) {
                            case 1: accountService.withdraw(cAccount, checkingBalance);
                                    break;
                            case 2: accountService.deposit(cAccount, checkingBalance);
                                    break;
                            case 3: accountService.transfer(cAccount, sAccount, checkingBalance, savingsBalance);
                                    break;
                            case 4: accountService.viewHistory(cAccount);
                                    break;
                            case 5: accountsView = false;
                                    break;
                        }
                    }
                } else if (option == 2) { //View Savings account.
                    accountsView = true;

                    SavingsAccount sAccount = accountService.getSavingsByOwner(loggedInUserId);
                    CheckingAccount cAccount = accountService.getCheckingByOwner(loggedInUserId);

                    if (sAccount.getSavingsId() == 0) {
                        System.out.println("ERROR: You don't have a Savings account! Please create one.");
                        accountsView = false;
                        continue;
                    }

                    savingsBalance = sAccount.getSavingsBalance();
                    checkingBalance = cAccount.getCheckingBalance();
                    savingsName = sAccount.getSavingsName();

                    while (loggedIn && accountsView && !savingsView) {
                        option = savingsMenu();
                        savingsBalance = sAccount.getSavingsBalance();

                        switch (option) {
                            case 1:
                                accountService.withdraw(sAccount, savingsBalance);
                                break;
                            case 2:
                                accountService.deposit(sAccount, savingsBalance);
                                break;
                            case 3:
                                accountService.transfer(sAccount, cAccount, savingsBalance, checkingBalance);
                                break;
                            case 4:
                                accountService.viewHistory(sAccount);
                                break;
                            case 5:
                                accountsView = false;
                                break;
                        }
                    }
                } else if (option == 3) { //Create accounts.
                    accountsView = true;

                    while (loggedIn && accountsView && !creationView) {
                        option = createAccountMenu();

                        switch (option) {
                            case 1:
                                accountService.createChecking();
                                break;
                            case 2:
                                accountService.createSavings();
                                break;
                            case 3:
                                accountsView = false;
                                break;
                        }
                    }
                } else if (option == 4) { //Log out.
                    System.out.println("Thank you for using David's Bank." +
                            "\nLogging you out...");
                    loggedIn = false;
                }
            }
        }
    }

    //region HELPER METHODS
    //Method that builds a menu from an array.
    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
    }

    // Non-account menus.
    public static int loginMenu() {
        int option = 0;

        System.out.print("========================" +
                "\nWelcome to David's Bank!" +
                "\n------------------------");
        System.out.println("\nTo navigate through this" +
                "\napplication, type a menu" +
                "\noption number and Enter." +
                "\n========================");
        String[] options = {"1 | Log in.",
                "2 | Register.",
                "3 | Exit."};

        while (option != 1 && option != 2 && option != 3) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine(); //Consume newline left-over

                while (option != 1 && option != 2 && option != 3) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int userNotFoundMenu() {
        int option = 0;

        String[] options = {"1 | Try again.",
                "2 | Cancel."};

        while (option != 1 && option != 2) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int passwordNotFoundMenu() {
        int option = 0;

        String[] options = {"1 | Try again.",
                "2 | Cancel."};

        while (option != 1 && option != 2) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int mainMenu() {
        int option = 0;

        System.out.print("=========" +
                "\nMAIN MENU" +
                "\n=========" + "\n");
        String[] options = {"1 | View your Checking account.",
                "2 | View your Savings account.",
                "3 | Create a new account.",
                "4 | Log out."};

        while (option != 1 && option != 2 && option != 3 && option != 4) {

            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2 && option != 3 && option != 4) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    //Account menus
    public static int checkingMenu() {
        int option = 0;

        System.out.print("================================" +
                "\nCHECKING ACCOUNT" +
                "\n--------------------------------");
        System.out.println("\nName: " + checkingName +
                "\n--------------------------------");
        System.out.println("Balance: " + formatter.format(checkingBalance) +
                "\n================================");
        String[] options = {"1 | Withdraw funds.",
                "2 | Deposit funds.",
                "3 | Transfer funds to Savings.",
                "4 | View transaction history.",
                "5 | Return."};

        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int savingsMenu() {
        int option = 0;

        System.out.print("==============================" +
                "\nSAVINGS ACCOUNT" +
                "\n-------------------------------");
        System.out.println("\nName: " + savingsName +
                "\n--------------------------------");
        System.out.println("Balance: " + formatter.format(savingsBalance) +
                "\n==============================");
        String[] options = {"1 | Withdraw funds.",
                "2 | Deposit funds.",
                "3 | Transfer funds to Checking.",
                "4 | View transaction history.",
                "5 | Return."};

        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int accountActionErrorMenu() {
        int option = 0;

        String[] options = {"1 | Enter a new amount.",
                "2 | Cancel."};

        while (option != 1 && option != 2) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }

    public static int createAccountMenu() {
        int option = 0;

        System.out.print("----------------" +
                "\nACCOUNT CREATION" +
                "\n----------------" + "\n");
        String[] options = {"1 | Create a Checking account.",
                "2 | Create a Savings account.",
                "3 | Return."};

        while (option != 1 && option != 2 && option != 3) {
            try {
                printMenu(options);
                option = input.nextInt();
                input.nextLine();

                while (option != 1 && option != 2 && option != 3) {
                    System.out.println("ERROR: Please choose a valid option:");
                    printMenu(options);
                    option = input.nextInt();
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Invalid input. Please enter an option number:");
                input.next();
            }
        }
        return option;
    }
    //endregion
}
