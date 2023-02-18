import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Drausin");

        User aUser = theBank.addUser("Jonh", "Doe", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currUser;
        while (true){

            currUser = ATM.mainMenuPronpt(theBank, scanner);

            ATM.printUserMenu(currUser, scanner);
        }

    }

    public static User mainMenuPronpt(Bank theBank, Scanner scanner){
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if (authUser == null){
                System.out.println("Incorrect ID/pin combination. " +
                "Please try again.");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scanner){

        theUser.printAccountsSummary();
        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdrawal");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println("");
            System.out.println("Enter choice");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please choice 1-5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice){

            case 1:
                ATM.showTransactionHistory(theUser, scanner);
                break;
            case 2:
                ATM.withdrawFunds(theUser, scanner);
                break;
            case 3:
                ATM.depositFunds(theUser, scanner);
                break;
            case 4:
                ATM.transferFunds(theUser, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;
        }
        if (choice != 5){
            ATM.printUserMenu(theUser, scanner);
        }

    }

    public static void showTransactionHistory(User theUser, Scanner scanner){

        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see:", theUser.numAccount());
            theAcct = scanner.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccount());

        theUser.printAcctTransHistory(theAcct);

    }
    public static void withdrawFunds(User theUser, Scanner scanner){
        int fromAcct;
        double amount;
        double acctBal;
        String  memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccount());
            fromAcct = scanner.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero. ");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f. \n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        scanner.nextLine();

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        theUser.addAcctTransaction(fromAcct, -1*amount, memo);


    }

    public static void depositFunds(User theUser, Scanner scanner){
        int toAcct;
        double amount;
        double acctBal;
        String  memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccount());
            toAcct = scanner.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());
        acctBal = theUser.getAccountBalance(toAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero. ");
            }
        } while (amount < 0);

        scanner.nextLine();

        System.out.println("Enter a memo");
        memo = scanner.nextLine();

        theUser.addAcctTransaction(toAcct, amount, memo);

    }

    public static void transferFunds(User theUser, Scanner scanner) {

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccount());
            fromAcct = scanner.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccount());
            toAcct = scanner.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero. ");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f. \n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));

        }

    }

