package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidPinException;
import model.Account;
import repository.AccountRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationService {
    private AccountRepository repository;
    public AuthenticationService(AccountRepository repository){
        this.repository = repository;
    }
    public Account login(Scanner scanner)
            throws BankException {

        System.out.println("\n--------LOGIN---------");

        System.out.print("Enter your Account Number: ");
        long accountNumber = scanner.nextLong();

        System.out.print("Enter PIN: ");
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

    public  void logout(){
        System.out.println("Logout Selected");
    }
}
