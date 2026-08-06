package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidPinException;
import model.Account;
import repository.AccountRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private AccountRepository repository;

    public AuthenticationService(AccountRepository repository){
        this.repository = repository;
    }
    public Account login(Scanner scanner) throws BankException {

        System.out.println("\n-------- CUSTOMER LOGIN ---------");

        System.out.print("Enter your Account Number : ");
        long accountNumber = scanner.nextLong();

        System.out.print("Enter PIN : ");
        int pin = scanner.nextInt();

       Account account = repository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException("Account Number not found.");
        }

        if (account.getPin() != pin) {
            throw new InvalidPinException("Incorrect PIN.");
        }

        System.out.println("\nLogin Successful!");
        System.out.println("Welcome " + account.getCustomerName());

        return account;
    }

    public boolean adminLogin(Scanner scanner){
        System.out.println("\n-------- ADMIN LOGIN ---------");
        scanner.nextLine();

        System.out.print("Enter Username : ");
        String username = scanner.nextLine();

        System.out.print("Enter Password : ");
        String password = scanner.nextLine();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("\n Admin Login Successful!");
            return true;
        }
        System.out.println("\nInvalid Admin Credentials");
        return false;
    }

    public void changePIN(Scanner scanner, Account loggedInAccount) throws BankException {
        if (loggedInAccount == null) {
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("\n---------- CHANGE PIN ----------");

        System.out.print("Enter Current PIN : ");
        int currentPin = scanner.nextInt();

        if (loggedInAccount.getPin() != currentPin) {
            throw new InvalidPinException("Current PIN is incorrect.");
        }

        System.out.print("Enter 4 Digit New PIN : ");
        int newPin = scanner.nextInt();

        if (newPin < 1000 || newPin > 9999) {
            throw new BankException("PIN must contain only 4 digits.");
        }

        if (newPin == currentPin) {
            throw new BankException("New PIN must be different from current PIN.");
        }

        loggedInAccount.setPin(newPin);
        repository.updateAccounts();

        System.out.println("\nPIN Changed Successfully!");
    }


    public  void logout(Account account){

        if(account == null){
            System.out.println("No user is currently logged in.");
            return;
        }

        System.out.println("\nGoodbye " + account.getCustomerName() + "!");
        System.out.println("Logout Successfully");
    }
}
