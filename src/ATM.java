import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Drausin");

        User aUser = theBank.addUser("Jonh", "Doe", "1234");

        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

    }
}
