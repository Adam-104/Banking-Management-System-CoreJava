package app;
import java.util.Scanner;

import exception.BankException;
import model.Account;
import repository.AccountRepository;
import service.AccountService;
import service.AuthenticationService;
import service.TransactionService;

import java.util.ArrayList;
public class Main {

    public static void showMenu(){
        System.out.println("================================");
        System.out.println("      BANK MANAGEMENT SYSTEM    ");
        System.out.println("==============================");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Check Balance");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. View Transaction History");
        System.out.println("7. Logout");
        System.out.println("8. Display All Accounts");
        System.out.println("9. Exit");
        System.out.println("=================================");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountRepository repository = new AccountRepository();
        AccountService accountService = new AccountService(repository);
        AuthenticationService authenticationService = new AuthenticationService(repository);
        TransactionService transactionService = new TransactionService(repository);
        Account loggedInAccount = null;
        int choice;
        do {
            showMenu();
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();

            try {

                switch (choice) {
                    case 1:
                        accountService.createAccount(scanner);
                        break;

                    case 2:
                        loggedInAccount = authenticationService.login(scanner);
                        break;

                    case 3:
                        transactionService.checkBalance(loggedInAccount);
                        break;

                    case 4:
                        transactionService.depositMoney(scanner, loggedInAccount);
                        break;

                    case 5:
                        transactionService.withdrawMoney(scanner, loggedInAccount);
                        break;

                    case 6:
                        transactionService.viewTransactionHistory(loggedInAccount);
                        break;

                    case 7:
                        authenticationService.logout();
                        break;

                    case 8:
                        accountService.displayAccounts();
                        break;

                    case 9:
                        System.out.println("\nThank you for using our Bank Management System.");
                        break;
                    default:
                        System.out.println("\nInvalid Choice! Please try again.");
                }
            }
            catch (BankException e){
                System.out.println("\n==============================");
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("==============================");
            }
        } while( choice != 8);
        scanner.close();
    }
}
