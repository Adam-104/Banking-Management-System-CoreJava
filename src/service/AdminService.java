package service;

import exception.BankException;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import repository.AccountRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private final AccountRepository repository;
    private final AuthenticationService authenticationService;

    public AdminService(AccountRepository repository) {
        this.repository = repository;
        this.authenticationService = new AuthenticationService(repository);
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

        List<Account> accounts = repository.findByCustomerName(customerName);

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

    public void showTotalBankBalance() {
        System.out.printf("\nTotal Balance        : ₹%.2f%n", repository.getTotalBalance());
    }

    public void showTotalCustomers() {
        System.out.println("\nTotal Customers : " + repository.getTotalCustomers());
    }

    public void sortAccounts(Scanner scanner) {
        List<Account> accounts = new ArrayList<>(repository.getAccounts());

        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        boolean sorting = true;

        while (sorting) {
            System.out.println("\n====================================================");
            System.out.println("                  SORT ACCOUNTS");
            System.out.println("====================================================");
            System.out.println("1. Account Number");
            System.out.println("2. Customer Name");
            System.out.println("3. Balance - Low to High");
            System.out.println("4. Balance - High to Low");
            System.out.println("5. Account Type");
            System.out.println("6. Back");
            System.out.println("====================================================");

            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    accounts.sort(Comparator.comparingLong(Account::getAccountNumber));

                    System.out.println("Sorted by Account Number");
                    displayAccounts(accounts);
                    break;
                case 2:
                    accounts.sort(Comparator.comparing(Account::getCustomerName, String.CASE_INSENSITIVE_ORDER));

                    System.out.println("Sorted by Customer Name");
                    displayAccounts(accounts);
                    break;
                case 3:
                    accounts.sort(Comparator.comparingDouble(Account::getBalance));
                    System.out.println("Sorted by Balance (Low to High). ");
                    displayAccounts(accounts);
                    break;
                case 4:
                    accounts.sort(Comparator.comparingDouble(Account::getBalance).reversed());
                    System.out.println("Sorted by Balance (High to Low). ");
                    displayAccounts(accounts);
                    break;
                case 5:
                    accounts.sort(Comparator.comparing(account -> account.getClass().getSimpleName()));
                    System.out.println("Sorted by Account Type");
                    displayAccounts(accounts);
                    break;
                case 6:
                    sorting = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public void shoeBankStatistics(Scanner scanner) {
        List<Account> accounts = repository.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        int totalCustomers = accounts.size();
        int savingsAccounts = 0;
        int currentAccounts = 0;
        double totalBalance = 0.0;
        double highestBalance = accounts.get(0).getBalance();
        double lowestBalance = accounts.get(0).getBalance();
        int totalTransactions = 0;

        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                savingsAccounts++;
            } else if (account instanceof CurrentAccount) {
                currentAccounts++;
            }

            totalBalance += account.getBalance();

            if (account.getBalance() > highestBalance) {
                highestBalance = account.getBalance();
            }

            if (account.getBalance() < lowestBalance) {
                lowestBalance = account.getBalance();
            }

            totalTransactions += account.getTransactions().size();
        }

        double averageBalance = totalBalance / totalCustomers;

        System.out.println("\n====================================================");
        System.out.println("                 BANK STATISTICS");
        System.out.println("====================================================");

        System.out.println("Total Customers       : " + totalCustomers);

        System.out.println("Savings Accounts      : " + savingsAccounts);

        System.out.println("Current Accounts      : " + currentAccounts);

        System.out.printf("Total Bank Balance    : ₹%.2f%n", totalBalance);

        System.out.printf("Average Balance       : ₹%.2f%n", averageBalance);

        System.out.printf("Highest Balance       : ₹%.2f%n", highestBalance);

        System.out.printf("Lowest Balance        : ₹%.2f%n", lowestBalance);

        System.out.println("Total Transactions    : " + totalTransactions);

        System.out.println("====================================================");
    }

    public void exportCustomerReport() {
        List<Account> accounts = repository.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        String fileName = "customer_report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("====================================================");
            writer.newLine();
            writer.write("                 BANK CUSTOMER REPORT");
            writer.newLine();
            writer.write("====================================================");
            writer.newLine();
            writer.newLine();

            for (Account account : accounts) {
                writer.write("Account Number: " + account.getAccountNumber());
                writer.newLine();

                writer.write("Customer Name: " + account.getCustomerName());
                writer.newLine();

                writer.write("Account Type: " + account.getClass().getSimpleName());
                writer.newLine();

                writer.write(String.format("Balance        : ₹%.2f", +account.getBalance()));
                writer.newLine();

                writer.write("Transactions: " + account.getTransactions().size());
                writer.newLine();

                writer.write("----------------------------------------------------");

                writer.newLine();
                writer.newLine();
            }

            writer.write("====================================================");
            writer.newLine();
            writer.write("                 END OF REPORT");
            writer.newLine();
            writer.write("====================================================");

            System.out.println("Customer Report exported Successfully.");

            System.out.println("File : " + fileName);

        } catch (IOException e) {
            System.out.println("Unable to Generate Customer Report.");
            System.out.println("Reason : " + e.getMessage());
        }
    }

    public void unlockAccount(Scanner scanner) throws BankException {
        authenticationService.unlockAccount(scanner);
    }

    private void displayAccounts(List<Account> accounts) {
        System.out.println("\n----------------------------------------------------");
        for (Account account : accounts) {
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Customer Name  : " + account.getCustomerName());
            System.out.printf("Balance         : ₹%.2f%n", account.getBalance());
            System.out.printf("Transactions    : %d", account.getTransactions().size());
            account.accountFeatures();
            System.out.println("--------------------------------------------------");
        }
    }
}
