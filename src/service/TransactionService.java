package service;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.SameAccountTransferException;
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

        System.out.print("Enter Deposit Amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            throw new InvalidAmountException("Deposit Amount must be greater than 0!");
        }

        double newBalance = loggedInAccount.getBalance() +  amount;
        loggedInAccount.setBalance(newBalance);
        notificationService.sendNotification(
                "₹" + amount + " Deposited Successfully!\n Current Balance : " + loggedInAccount.getBalance());
        System.out.println("current Balance:  " + loggedInAccount.getBalance());

        loggedInAccount.getTransactions().add(new
                Transaction("Deposit", amount, "Cash Deposit")
        );
    }

    public void withdrawMoney(Scanner scanner, Account loggedInAccount) throws InvalidAmountException,
            InsufficientBalanceException {
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }

        System.out.print("Enter Withdraw Amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            throw new InvalidAmountException("Withdrawal amount must be greater than zero!");
        }

        if (amount > loggedInAccount.getBalance()){
            throw new InsufficientBalanceException("Insufficient Balance.");
        }

        loggedInAccount.setBalance(loggedInAccount.getBalance() - amount);

        notificationService.sendNotification(
                "₹" + amount + " Withdraw Successfully!\nCurrent Balance : " + loggedInAccount.getBalance());
        System.out.println("current Balance:  " + loggedInAccount.getBalance());

        loggedInAccount.getTransactions().add(new
                Transaction("withdrawal", amount,  "Cash Withdrawal")
        );
    }

    public void transferMoney(Scanner scanner, Account loggedInAccount) throws InvalidAmountException,
            InsufficientBalanceException, AccountNotFoundException, SameAccountTransferException {
        if  (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }
        System.out.print("Enter Receiver's Account Number : ");
        long receiverAccountNumber = scanner.nextLong();

        if (receiverAccountNumber == loggedInAccount.getAccountNumber()){
            throw new SameAccountTransferException("You cannot transfer money to your own Account.");
        }

        Account receiverAccount = repository.findByAccountNumber(receiverAccountNumber);

        if (receiverAccount == null){
            throw new AccountNotFoundException("Receiver Account Not Found.");
        }

        System.out.print("Enter Amount to Transfer : ");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            throw new InvalidAmountException("Transfer Amount must be greater than zero!");
        }

        if (amount > loggedInAccount.getBalance()){
            throw new InsufficientBalanceException("Insufficient Balance.");
        }

        loggedInAccount.setBalance(loggedInAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        loggedInAccount.getTransactions().add(
                new Transaction("Transfer", amount, "Transferred to A/C : " + receiverAccount.getAccountNumber()));
        receiverAccount.getTransactions().add(
                new Transaction("Received", amount, "Received from A/C : " + loggedInAccount.getAccountNumber()));
        notificationService.sendNotification(
                amount + " Transferred Successfully to Account " + receiverAccount.getAccountNumber()
                        + "!\nCurrent Balance : " + loggedInAccount.getBalance());

        System.out.println("Transfer Successful!");
        System.out.println("Current Balance : " + loggedInAccount.getBalance());

    }

    public void viewTransactionHistory(Account loggedInAccount){
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("\n=========== Transaction History ===========");
        for (Transaction transaction : loggedInAccount.getTransactions()){
            System.out.println(transaction);
        }

        System.out.println("Current Balance:  " + loggedInAccount.getBalance());
    }

    public void miniStatement(Account loggedInAccount, int limit){
        if (loggedInAccount == null){
            System.out.println("Please Login First!");
        }

        if (loggedInAccount.getTransactions().isEmpty()){
            System.out.println("No Transactions Found!");
        }

        System.out.println("\n=========== Transaction History ===========");

        int totalTransactions = loggedInAccount.getTransactions().size();

        int start = Math.max(0, totalTransactions - limit);

        for (int i = start; i < totalTransactions; i++){
            System.out.println(loggedInAccount.getTransactions().get(i));
        }

        System.out.println("\nCurrent Balance:  ₹" + loggedInAccount.getBalance());

        System.out.println("===========================================");
    }
}
