package service;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.Account;
import model.Transaction;

import java.util.Scanner;
import notification.NotificationService;
import notification.SMSNotification;
import repository.AccountRepository;

public class TransactionService {
    private AccountRepository repository;
    private NotificationService notificationService;
    public TransactionService(AccountRepository repository){
        this.repository = repository;
        notificationService = new SMSNotification();
    }
    public void checkBalance(Account loggedInAccount){
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("\n-----------Account Details----------");
        System.out.println("Account Number : " + loggedInAccount.getAccountNumber());
        System.out.println("Customer Name  : " + loggedInAccount.getCustomerName());
        System.out.println("Current Balance: " + loggedInAccount.getBalance());
        System.out.println();
    }

    public void depositMoney(Scanner scanner, Account loggedInAccount) throws InvalidAmountException {
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("Enter Deposit Amount: ");
        double amount = scanner.nextDouble();

        if (amount < 0){
            throw new InvalidAmountException("Deposit Amount must be greater than 0!");
        }

        double newBalance = loggedInAccount.getBalance() +  amount;
        loggedInAccount.setBalance(newBalance);
        notificationService.sendNotification(
                amount + " Deposited Successfully!\n Current Balance : " + loggedInAccount.getBalance());
        System.out.println("current Balance:  " + loggedInAccount.getBalance());

        loggedInAccount.getTransactions().add(new Transaction("Deposited", amount));
    }

    public void withdrawMoney(Scanner scanner, Account loggedInAccount) throws InvalidAmountException,
            InsufficientBalanceException {
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
        }

        System.out.println("Enter Withdraw Amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            throw new InvalidAmountException("Withdrawal amount must be greater than zero!");
        }

        if (amount > loggedInAccount.getBalance()){
            throw new InsufficientBalanceException("Insuffiecnt Balance.");
        }

        loggedInAccount.setBalance(loggedInAccount.getBalance() - amount);

        notificationService.sendNotification(
                amount + " Withdraw Successfully!\nCurrent Balance : " + loggedInAccount.getBalance());
        System.out.println("current Balance:  " + loggedInAccount.getBalance());

        loggedInAccount.getTransactions().add(new Transaction("Withdrawn", amount));
    }

    public void viewTransactionHistory(Account loggedInAccount){
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("\n=========== Transaction History ===========");
        for (Transaction transaction : loggedInAccount.getTransactions()){
            System.out.println(transaction.getType() + " : " + transaction.getAmount());
        }

        System.out.println("Current Balance:  " + loggedInAccount.getBalance());
    }
}
