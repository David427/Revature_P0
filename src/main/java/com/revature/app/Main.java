package com.revature.app;

import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.repositories.AccountRepoImp;
import com.revature.repositories.UserRepoImp;
import com.revature.services.AccountServiceImp;
import com.revature.services.UserServiceImp;
import com.revature.util.LinkedList;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    //Declare & initialize vars.
    static Scanner input = new Scanner(System.in);
    static UserRepoImp userRepo = new UserRepoImp();
    static UserServiceImp userService = new UserServiceImp(userRepo);
    static AccountRepoImp accountRepo = new AccountRepoImp();
    static AccountServiceImp accountService = new AccountServiceImp(accountRepo);
    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    static boolean loggedIn = false;
    static int loggedInUserId = 0;
    static boolean accountsView = false;
    static boolean checkingView = false;
    static boolean savingsView = false;
    static String checkingName;
    static String savingsName;
    static double checkingBalance = 0;
    static double savingsBalance = 0;

    public static void main(String[] args) {

        //Declare vars.
        int option;

        //Main app loop logic.
        while (!loggedIn) {
            option = loginMenu();

            if (option == 1) {
                System.out.println("==========" +
                        "\nUSER LOGIN" +
                        "\n==========");
                System.out.println("Enter your username.");
                String userLogin = input.nextLine();
                User user = userService.findUser(userLogin);

                while (user == null) {
                    System.out.println("ERROR: User not found.");
                    option = userNotFoundMenu();

                    if (option == 1) {
                        System.out.println("Enter your username.");
                        userLogin = input.nextLine();
                        user = userService.findUser(userLogin);
                    } else {
                        break;
                    }
                }

                if (user != null) {
                    System.out.println("Enter your password.");
                    String userPassword = input.nextLine();

                    while (!Objects.equals(user.getUserPassword(), userPassword)) {
                        System.out.println("ERROR: Invalid password. Please try again.");
                        option = passwordNotFoundMenu();

                        if (option == 1) {
                            System.out.println("Enter your password.");
                            userPassword = input.nextLine();
                        } else {
                            break;
                        }
                    }

                    if (Objects.equals(user.getUserPassword(), userPassword)) {
                        System.out.println("Login successful! Welcome.");
                        loggedInUserId = user.getUserId();
                        loggedIn = true;
                    }
                }
            } else if (option == 2) {
                System.out.println("========" +
                        "\nNEW USER" +
                        "\n========");
                System.out.println("Enter a username.");
                String newLogin = input.nextLine();

                //Populate a list of users from the db to perform unique username checking.
                LinkedList<User> userList = userService.getAllUsers();
                boolean userExists = userList.checkIfExists(newLogin);

                while (userExists) {
                    System.out.println("ERROR: Username unavailable. Please enter a different one.");
                    newLogin = input.nextLine();
                    userExists = userList.checkIfExists(newLogin);
                }

                System.out.println("Thank you. Now, create a password.");
                String newPassword = input.nextLine();
                System.out.println("Password confirmation. Type your password again.");
                String newPasswordConfirm = input.nextLine();

                while (!Objects.equals(newPasswordConfirm, newPassword)) {
                    System.out.println("ERROR: Passwords do not match. Try again.");
                    newPasswordConfirm = input.nextLine();
                }

                User user = new User(newLogin, newPassword);
                userService.addUser(user);
                System.out.println("Account successfully created!");
            } else if (option == 3) {
                System.out.println("Thank you for visiting David's Bank. Goodbye.");
                exit(0);
            }

            while (loggedIn && !accountsView) {
                option = mainMenu();

                if (option == 1) {
                    accountsView = true;

                    //Retrieve the account belonging to the currently logged in user.
                    CheckingAccount cAccount = accountService.getCheckingByOwner(loggedInUserId);

                    if (cAccount == null) {
                        System.out.println("ERROR: You don't have a Checking account! Please create one.");
                        accountsView = false;
                        continue;
                    }

                    //Retrieve balance and friendly account name.
                    checkingBalance = cAccount.getCheckingBalance();
                    checkingName = cAccount.getCheckingName();

                    while (loggedIn && accountsView && !checkingView) {
                        option = checkingMenu();
                        checkingBalance = cAccount.getCheckingBalance();

                        if (option == 1) {
                            checkingView = true;
                            double withdrawal = 0;
                            boolean validWithdrawal = false;
                            boolean cancelWithdrawal = false;

                            System.out.println("Enter an amount to withdraw in the following format: ##.##");
                            while (!validWithdrawal && !cancelWithdrawal) {
                                try {
                                    withdrawal = input.nextDouble();

                                    while (withdrawal > checkingBalance || withdrawal < 0) {
                                        System.out.println("ERROR: Invalid withdrawal amount.");
                                        option = accountActionErrorMenu();

                                        if (option == 1) {
                                            System.out.println("Enter a new amount.");
                                            withdrawal = input.nextDouble();
                                        } else if (option == 2) {
                                            cancelWithdrawal = true;
                                            withdrawal = 0;
                                            break;
                                        }
                                    }

                                    if (withdrawal <= checkingBalance) {
                                        validWithdrawal = true;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("ERROR: Invalid input. Enter an amount to withdraw in the following format: ##.##");
                                    input.next();
                                }
                            }
                            cAccount.setCheckingBalance(checkingBalance - withdrawal);
                            accountService.updateChecking(cAccount);
                            checkingBalance = cAccount.getCheckingBalance();
                            checkingView = false;
                        } else if (option == 2) {
                            checkingView = true;
                            double deposit = 0;
                            boolean validDeposit = false;
                            boolean cancelDeposit = false;

                            System.out.println("Enter an amount to deposit in the following format: ##.##");
                            while (!validDeposit && !cancelDeposit) {
                                try {
                                    deposit = input.nextDouble();

                                    while (deposit < 0) {
                                        System.out.println("ERROR: Cannot deposit a negative amount.");
                                        option = accountActionErrorMenu();

                                        if (option == 1) {
                                            System.out.println("Enter a new amount.");
                                            deposit = input.nextDouble();
                                        } else if (option == 2) {
                                            cancelDeposit = true;
                                            deposit = 0;
                                            break;
                                        }
                                    }

                                    if (deposit > 0) {
                                        validDeposit = true;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("ERROR: Invalid input. Enter an amount to withdraw in the following format: ##.##");
                                    input.next();
                                }
                            }
                            cAccount.setCheckingBalance(checkingBalance + deposit);
                            accountService.updateChecking(cAccount);
                            checkingBalance = cAccount.getCheckingBalance();
                            checkingView = false;
                        } else if (option == 3) {
                            checkingView = true;
                            System.out.println("checkingAccount.transferToSavings();");
                        } else if (option == 4) {
                            checkingView = true;
                            System.out.println("checkingAccount.viewHistory();");
                        } else if (option == 5) {
                            accountsView = false;
                        }
                    }
                } else if (option == 2) {
                    accountsView = true;

                    SavingsAccount sAccount = accountService.getSavingsByOwner(loggedInUserId);

                    if (sAccount == null) {
                        System.out.println("ERROR: You don't have a Savings account! Please create one.");
                        accountsView = false;
                        continue;
                    }

                    savingsBalance = sAccount.getSavingsBalance();
                    savingsName = sAccount.getSavingsName();

                    while (loggedIn && accountsView && !savingsView) {
                        option = savingsMenu();
                        savingsBalance = sAccount.getSavingsBalance();

                        if (option == 1) {
                            savingsView = true;
                            double withdrawal = 0;
                            boolean validWithdrawal = false;
                            boolean cancelWithdrawal = false;

                            System.out.println("Enter an amount to withdraw in the following format: ##.##");
                            while (!validWithdrawal && !cancelWithdrawal) {
                                try {
                                    withdrawal = input.nextDouble();

                                    while (withdrawal > savingsBalance || withdrawal < 0) {
                                        System.out.println("ERROR: Invalid withdrawal amount.");
                                        option = accountActionErrorMenu();

                                        if (option == 1) {
                                            System.out.println("Enter a new amount.");
                                            withdrawal = input.nextDouble();
                                        } else if (option == 2) {
                                            cancelWithdrawal = true;
                                            withdrawal = 0;
                                            break;
                                        }
                                    }

                                    if (withdrawal <= savingsBalance) {
                                        validWithdrawal = true;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("ERROR: Invalid input. Enter an amount to withdraw in the following format: ##.##");
                                    input.next();
                                }
                            }
                            sAccount.setSavingsBalance(savingsBalance - withdrawal);
                            accountService.updateSavings(sAccount);
                            savingsBalance = sAccount.getSavingsBalance();
                            savingsView = false;
                        } else if (option == 2) {
                            checkingView = true;
                            double deposit = 0;
                            boolean validDeposit = false;
                            boolean cancelDeposit = false;

                            System.out.println("Enter an amount to deposit in the following format: ##.##");
                            while (!validDeposit && !cancelDeposit) {
                                try {
                                    deposit = input.nextDouble();

                                    while (deposit < 0) {
                                        System.out.println("ERROR: Cannot deposit a negative amount.");
                                        option = accountActionErrorMenu();

                                        if (option == 1) {
                                            System.out.println("Enter a new amount.");
                                            deposit = input.nextDouble();
                                        } else if (option == 2) {
                                            cancelDeposit = true;
                                            deposit = 0;
                                            break;
                                        }
                                    }

                                    if (deposit > 0) {
                                        validDeposit = true;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("ERROR: Invalid input. Enter an amount to withdraw in the following format: ##.##");
                                    input.next();
                                }
                            }
                            sAccount.setSavingsBalance(savingsBalance + deposit);
                            accountService.updateSavings(sAccount);
                            savingsBalance = sAccount.getSavingsBalance();
                            savingsView = false;
                        } else if (option == 3) {
                            System.out.println("savingsAccount.transferToChecking();");
                        } else if (option == 4) {
                            System.out.println("savingsAccount.viewHistory();");
                        } else if (option == 5) {
                            accountsView = false;
                        }
                    }
                } else if (option == 3) {
                    accountsView = true;
                    option = createAccountMenu();

                    if (option == 1) {
                        // checkingAccount.create();
                        accountsView = false;
                    } else if (option == 2) {
                        // savingsAccount.create();
                        accountsView = false;
                    } else if (option == 3) {
                        accountsView = false;
                    }
                } else if (option == 4) {
                    System.out.println("Thank you for using David's Bank." +
                            "\nLogging you out...");
                    loggedIn = false;
                }
            }
        }
        System.out.println("END OF CODE");
    }

    //region HELPER METHODS
    //Method that builds a menu from an array.
    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
    }

    // Menu methods.
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

    private static int accountActionErrorMenu() {
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
