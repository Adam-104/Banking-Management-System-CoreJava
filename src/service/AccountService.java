package service;
import exception.AccountAlreadyExistsException;
import exception.BankException;
import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import notification.NotificationService;
import notification.SMSNotification;
import repository.AccountRepository;

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

        System.out.print("Enter Your Choice : ");
        int type = scanner.nextInt();

        if(type != 1 && type != 2){
            throw new BankException("Invalid Account Type.");
        }

        long accountNumber = repository.generateUniqueAccountNumber();
        scanner.nextLine();

        System.out.print("Enter Customer Name : ");
        String customerName = scanner.nextLine().trim();

        if (customerName.isEmpty()){
            throw new BankException("Customer name cannot be  empty");
        }

        System.out.print("Create 4 DIGIT PIN : ");
        int pin = scanner.nextInt();

        if (pin < 1000 || pin > 9999){
            throw new BankException("PIN must contain only 4 digits");
        }

        System.out.print("Enter Initial Deposit : ");
        double balance = scanner.nextDouble();

        if (balance <= 0){
            throw new BankException("Initial deposit must be greater than zero.");
        }

        Account account;

        if(type == 1){
            account = new SavingsAccount(accountNumber, customerName, pin, balance);
        } else {
            account = new CurrentAccount(accountNumber, customerName, pin, balance);
        }

        repository.addAccount(account);

        notificationService.sendNotification(
                "Account Created Successfully."
        );
        System.out.println("-------------------------------------");
        System.out.println("\n Account Created Successfully!");
        System.out.println("Generated Account Number: " + accountNumber);
        System.out.println("-------------------------------------");

    }

    public  void displayAccounts(){
        if(repository.getAccounts().isEmpty()){
            System.out.println("No Accounts Found!");
            return;
        }
        System.out.println("\n============ ACCOUNT LIST ============");

        for(Account account : repository.getAccounts()){
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Customer Name  : " + account.getCustomerName());
//            System.out.println("Pin            : " + account.getPin());
            System.out.println("Balance        : " + account.getBalance());
            account.accountFeatures();
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }
}
