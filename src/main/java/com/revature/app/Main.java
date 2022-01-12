package com.revature.app;

import com.revature.models.User;
import com.revature.util.FileDB;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    // Declare & initialize vars.
    static Scanner in = new Scanner(System.in);
    static boolean loggedIn = false;
    static boolean viewingAccounts = false;

    public static void main(String[] args) throws IOException {

        // Shutdown hook - call shutdown() to write userList to the file.
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    FileDB.shutdown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "shutdown-thread"));

        // Declare vars.
        int option;

        // Run startup() to populate userList.
        FileDB.startup();
        // Verify userList is populated.
        System.out.println(FileDB.userList);

        // Run app.
        while (!loggedIn) {
            option = loginMenu();

            if (option == 1) { // Login
                System.out.println("==========" +
                        "\nUSER LOGIN" +
                        "\n==========");
                System.out.println("Enter your username.");
                String username = in.nextLine();

                // Search in userList for the input string.
                boolean result = FileDB.userList.search(username);
                System.out.println(result);

                loggedIn = true;
            } else if (option == 2) { // Register
                System.out.println("========" +
                        "\nNEW USER" +
                        "\n========");
                System.out.println("Enter a username.");
                String newName = in.nextLine();
                System.out.println("Thank you. Now, create a password.");
                String newPassword = in.nextLine();
                System.out.println("Password confirmation. Type your password again.");
                String newPasswordConfirm = in.nextLine();

                while (!Objects.equals(newPasswordConfirm, newPassword)) {
                    System.out.println("Passwords do not match. Try again.");
                    newPasswordConfirm = in.nextLine();
                }

                System.out.println("Account successfully created!");
                User user = new User(newName, newPassword);
                FileDB.userList.add(user);
            } else if (option == 3) {
                System.out.println("Thank you for visiting David's Bank. Goodbye.");
                exit(0);
            }

            // // Main Menu
            // while(loggedIn && !viewingAccounts) {
            //     option = mainMenu();
            //
            //     if (option == 1) {
            //         viewingAccounts = true;
            //         option = checkingMenu();
            //
            //         if (option == 1) {
            //             System.out.println("checkingAccount.withdraw();");
            //             // checkingAccount.withdraw();
            //         } else if (option == 2) {
            //             System.out.println("checkingAccount.deposit();");
            //             // checkingAccount.deposit();
            //         } else if (option == 3) {
            //             System.out.println("checkingAccount.transferToSavings();");
            //             // checkingAccount.transferToSavings();
            //         } else if (option == 4) {
            //             System.out.println("checkingAccount.viewHistory();");
            //             // checkingAccount.viewHistory();
            //         }
            //         else if (option == 5) {
            //             viewingAccounts = false;
            //         }
            //     } else if (option == 2) {
            //         viewingAccounts = true;
            //         option = savingsMenu();
            //
            //         if (option == 1) {
            //             System.out.println("savingsAccount.withdraw();");
            //             // savingsAccount.withdraw();
            //         } else if (option == 2) {
            //             System.out.println("savingsAccount.deposit();");
            //             // savingsAccount.deposit();
            //         } else if (option == 3) {
            //             System.out.println("savingsAccount.transferToChecking();");
            //             // savingsAccount.transferToChecking();
            //         } else if (option == 4) {
            //             System.out.println("savingsAccount.viewHistory();");
            //             // savingsAccount.viewHistory();
            //         } else if (option == 5) {
            //             viewingAccounts = false;
            //         }
            //     } else if (option == 3) {
            //         viewingAccounts = true;
            //         option = createAccountMenu();
            //
            //         if (option == 1) {
            //             // checkingAccount.create();
            //             viewingAccounts = false;
            //         } else if (option == 2) {
            //             // savingsAccount.create();
            //             viewingAccounts = false;
            //         } else if (option == 3) {
            //             viewingAccounts = false;
            //         }
            //     } else if (option == 4) {
            //         System.out.println("Thank you for using David's Bank." +
            //                 "\nLogging you out...");
            //         loggedIn = false;
            //     }
            // }
        }

        System.out.println("END OF CODE");
    }

    // Method that builds a menu from an array.
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
        String[] options = {"1 | Log in to your account.",
                "2 | Register for a new account.",
                "3 | Exit."};

        while (option != 1 && option != 2 && option != 3) {
            try {
                printMenu(options);
                option = in.nextInt();
                in.nextLine(); // Consume newline left-over

                while (option != 1 && option != 2 && option != 3) {
                    System.out.println("Please choose a valid option:");
                    printMenu(options);
                    option = in.nextInt();
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter an option number:");
                in.next();
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
                "3 | Create an account.",
                "4 | Log out."};

        while (option != 1 && option != 2 && option != 3 && option != 4) {

            try {
                printMenu(options);
                option = in.nextInt();
                in.nextLine(); // Consume newline left-over

                while (option != 1 && option != 2 && option != 3 && option != 4) {
                    System.out.println("Please choose a valid option:");
                    printMenu(options);
                    option = in.nextInt();
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter an option number:");
                in.next();
            }
        }
        return option;
    }

    // Account menus
    public static int checkingMenu() {
        int option = 0;

        System.out.print("================" +
                "\nCHECKING ACCOUNT" +
                "\n----------------");
        System.out.println("\nBalance: "/* + checkingAccount.balance */ +
                "\n================");
        String[] options = {"1 | Withdraw funds.",
                "2 | Deposit funds.",
                "3 | Transfer funds to Savings.",
                "4 | View transaction history.",
                "5 | Return."};

        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
            try {
                printMenu(options);
                option = in.nextInt();
                in.nextLine(); // Consume newline left-over

                while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
                    System.out.println("Please choose a valid option:");
                    printMenu(options);
                    option = in.nextInt();
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter an option number:");
                in.next();
            }
        }
        return option;
    }

    public static int savingsMenu() {
        int option = 0;

        System.out.print("===============" +
                "\nSAVINGS ACCOUNT" +
                "\n---------------");
        System.out.println("\nBalance: "/* + savingsAccount.balance */ +
                "\n===============");
        String[] options = {"1 | Withdraw funds.",
                "2 | Deposit funds.",
                "3 | Transfer funds to Checking.",
                "4 | View transaction history.",
                "5 | Return."};

        while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
            try {
                printMenu(options);
                option = in.nextInt();
                in.nextLine(); // Consume newline left-over

                while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5) {
                    System.out.println("Please choose a valid option:");
                    printMenu(options);
                    option = in.nextInt();
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter an option number:");
                in.next();
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
                option = in.nextInt();
                in.nextLine(); // Consume newline left-over

                while (option != 1 && option != 2 && option != 3) {
                    System.out.println("Please choose a valid option:");
                    printMenu(options);
                    option = in.nextInt();
                }
            } catch (Exception ex) {
                System.out.println("Invalid input. Please enter an option number:");
                in.next();
            }
        }
        return option;
    }

    // NEXT MENU

}
