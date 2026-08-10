package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidPinException;
import model.Account;
import repository.AccountRepository;

import java.util.Scanner;

public class AuthenticationService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private AccountRepository repository;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public AuthenticationService(AccountRepository repository){
        this.repository = repository;
    }
    public Account login(Scanner scanner) throws BankException {

        System.out.println("\n-------- CUSTOMER LOGIN ---------");

        System.out.print("Enter your Account Number : ");
        long accountNumber = scanner.nextLong();

        Account account = repository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException("Account Number not found.");
        }

        if(account.isLocked()){
            throw new BankException("Your Account is locked. " + "Please contact the Bank Administrator.");
        }

        System.out.print("Enter PIN : ");
        int pin = scanner.nextInt();

        if (account.getPin() == pin) {
            account.setFailedLoginAttempts(0);
            repository.updateAccounts();
            System.out.println("\nLogin Successful.!");
            System.out.println("Welcome " + account.getCustomerName());
            return account;
        }

        int attempts = account.getFailedLoginAttempts() + 1;
        account.setFailedLoginAttempts(attempts);

        if(attempts >=  MAX_LOGIN_ATTEMPTS){
            account.setLocked(true);

            repository.updateAccounts();

            System.out.println("\n======================================");
            System.out.println("          ACCOUNT LOCKED");
            System.out.println("======================================");

            System.out.println("You Entered the wrong PIN. " + MAX_LOGIN_ATTEMPTS + " times.");
            System.out.println("Please Contact the Bank Administrator.");

            throw new InvalidPinException("Account Locked due to multiple incorrect PIN Attempts.");

        }

        repository.updateAccounts();

        int remainingAttempts = MAX_LOGIN_ATTEMPTS - attempts;

        throw new InvalidPinException("Incorrect PIN. Attempts Remaining " + remainingAttempts);

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

    public void unlockAccount(Scanner scanner) throws BankException {
        System.out.println("\n========== UNLOCK ACCOUNT ==========");
        System.out.print("Enter Account Number : ");
        long accountNumber = scanner.nextLong();

        Account account = repository.findByAccountNumber(accountNumber);

        if(account == null){
            throw new AccountNotFoundException("Account Number not found.");
        }

        if(!account.isLocked()){
            System.out.println("\nAccount already unlocked.");
            return;
        }
        account.setLocked(false);
        account.setFailedLoginAttempts(0);

        System.out.println("\nAccount unlocked Successfully.");

        System.out.println("Customer Name : " + account.getCustomerName());
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
