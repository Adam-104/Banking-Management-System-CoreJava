package service;

import model.Account;
import repository.AccountRepository;

import java.util.List;
import java.util.Scanner;

public class AdminService {
    private final AccountRepository repository;
    public AdminService(AccountRepository repository) {
        this.repository = repository;
    }

    public void displayAllAccounts() {
        if (repository.getAccounts().isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }
        System.out.println("\n============= ACCOUNT LIST =============");
        for (Account account : repository.getAccounts()) {
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Customer Name  : " + account.getCustomerName());
            System.out.printf("Balance        : ₹%.2f%n", account.getBalance());

            account.accountFeatures();

            System.out.println("--------------------------------------");
        }
    }

    public void searchAccount(Scanner scanner) {
        System.out.print("Enter Account Number : ");
        long accountNumber = scanner.nextLong();

        Account account = repository.findByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account number " + accountNumber + " not found!");
            return;
        }

        System.out.println("\n============= ACCOUNT DETAILS =============");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Customer Name : " + account.getCustomerName());
        System.out.printf("Balance        : ₹%.2f%n", account.getBalance());

        account.accountFeatures();
    }

    public void searchCustomerByName(Scanner scanner) {
        scanner.nextLine();

        System.out.print("Enter Customer Name : ");
        String customerName = scanner.nextLine().trim();

        List<Account> accounts =
                repository.findByCustomerName(customerName);

        if (accounts.isEmpty()) {
            System.out.println("Customer " + customerName + " not found!");
            return;
        }

        System.out.println("\n=============== Search Results =================");

        for (Account account : accounts) {
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Customer Name  : " + account.getCustomerName());
            System.out.println("Balance        : ₹" + account.getBalance());
            System.out.println("Transactions   : " + account.getTransactions().size());

            account.accountFeatures();

            System.out.println("-----------------------------------------------");
        }
    }

    public void deleteAccount(Scanner scanner) {
        System.out.print("Enter Account Number : ");
        long accountNumber = scanner.nextLong();

        Account account = repository.findByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account number " + accountNumber + " not found!");
            return;
        }

        repository.deleteAccount(account);

        System.out.println("Account Deleted Successfully!");
    }

    public void showTotalCustomers(){
        System.out.println("\nTotal Customers : " + repository.getTotalCustomers());
    }

    public void showTotalbankBalance(){
        System.out.printf("\nTotal Balance        : ₹%.2f%n", repository.getTotalBalance());
    }
}
