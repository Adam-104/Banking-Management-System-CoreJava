package service;
import exception.AccountAlreadyExistsException;
import exception.BankException;
import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.Transaction;
import notification.NotificationService;
import notification.SMSNotification;
import repository.AccountRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountService {
    private NotificationService notificationService;
    private AccountRepository repository;
    public AccountService(AccountRepository repository){
        this.repository = repository;
        notificationService = new SMSNotification();
    }
    public  void createAccount(Scanner scanner) throws BankException {

        System.out.println("\n--------CREATE ACCOUNT---------");

        System.out.println("Select Account Type: ");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");

        System.out.println("Enter Your Choice: ");
        int type = scanner.nextInt();

        System.out.println("Enter your Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine();

       Account exixtingAccount = repository.findByAccountNumber(accountNumber);

       if (exixtingAccount != null){
           throw  new AccountAlreadyExistsException("Account already exists");
       }

        System.out.println("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        System.out.println("Create 4 DIGIT PIN: ");
        int pin = scanner.nextInt();

        System.out.println("Enter Initial Deposit: ");
        double balance = scanner.nextDouble();

        Account account;
        if(type == 1){
            account = new SavingsAccount(accountNumber, customerName, pin, balance);
        } else {
            account = new CurrentAccount(accountNumber, customerName, pin, balance);
        }
        repository.addAccount(account);

        notificationService.sendNotification("Account Created Successfully.");
        System.out.println("\n Account Created Successfully!");

    }

    public  void displayAccounts(){
        if(repository.getAccounts().isEmpty()){
            System.out.println("No Accounts Found!");
            return;
        }
        System.out.println("\n============ Account LIST ============");

        for(Account account : repository.getAccounts()){
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Customer Name  : " + account.getCustomerName());
            System.out.println("Pin            : " + account.getPin());
            System.out.println("Balance        : " + account.getBalance());
           account.accountFeatures();
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }
}
