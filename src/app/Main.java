package app;

import exception.BankException;
import model.Account;
import repository.AccountRepository;
import service.AccountService;
import service.AdminService;
import service.AuthenticationService;
import service.TransactionService;
import util.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountRepository repository = new AccountRepository();

        AccountService accountService = new AccountService(repository);
        AuthenticationService authenticationService = new AuthenticationService(repository);
        TransactionService transactionService = new TransactionService(repository);
        AdminService adminService = new AdminService(repository);

        boolean applicationRunning = true;
        while (applicationRunning) {
            Menu.showMainMenu();

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        accountService.createAccount(scanner);
                        break;
                    case 2:
                        Account loggedInAccount = authenticationService.login(scanner);

                        boolean customerLoggedIn = true;
                        while (customerLoggedIn) {
                            Menu.showCustomerMenu(loggedInAccount);

                            int customerChoice = scanner.nextInt();

                            try {
                                switch (customerChoice) {
                                    case 1:
                                        transactionService.checkBalance(loggedInAccount);
                                        break;
                                    case 2:
                                        transactionService.depositMoney(scanner, loggedInAccount);
                                        repository.updateAccounts();
                                        break;
                                    case 3:
                                        transactionService.withdrawMoney(scanner, loggedInAccount);
                                        repository.updateAccounts();
                                        break;
                                    case 4:
                                        transactionService.transferMoney(scanner, loggedInAccount);
                                        repository.updateAccounts();
                                        break;
                                    case 5:
                                        transactionService.viewTransactionHistory(loggedInAccount);
                                        break;
                                    case 6:
                                        transactionService.miniStatement(loggedInAccount, 5);
                                        break;
                                    case 7:
                                        authenticationService.changePIN(scanner, loggedInAccount);
                                        break;
                                    case 8:
                                        authenticationService.logout(loggedInAccount);
                                        customerLoggedIn = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice.");
                                }
                            } catch (Exception e) {
                                System.out.println("\n ERROR : " + e.getMessage());
                            }
                        }
                        break;
                    case 3:
                        boolean adminLoggedIn = authenticationService.adminLogin(scanner);

                        while (adminLoggedIn) {
                            Menu.showAdminMenu();
                            int adminChoice = scanner.nextInt();
                            try {
                                switch (adminChoice) {
                                    case 1:
                                        adminService.displayAllAccounts();
                                        break;
                                    case 2:
                                        adminService.searchAccount(scanner);
                                        break;
                                    case 3:
                                        adminService.searchCustomerByName(scanner);
                                        break;
                                    case 4:
                                        adminService.deleteAccount(scanner);
                                        break;
                                    case 5:
                                        adminService.showTotalCustomers();
                                        break;
                                    case 6:
                                        adminService.showTotalBankBalance();
                                        break;
                                    case 7:
                                        adminService.sortAccounts(scanner);
                                        break;
                                    case 8:
                                        adminService.shoeBankStatistics(scanner);
                                        break;
                                    case 9:
                                        adminService.exportCustomerReport();
                                        break;
                                    case 10:
                                        adminService.unlockAccount(scanner);
                                        break;
                                    case 11:
                                        adminLoggedIn = false;
                                        System.out.println("\nAdmin Logged Out Successfully.");
                                        break;
                                    default:
                                        System.out.println("\nInvalid choice.");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 4:
                        applicationRunning = false;
                        System.out.println("\n===================================");
                        System.out.println("Thank You For Using Our Bank");
                        System.out.println("Have a nice day!");
                        System.out.println("===================================");
                        break;
                }
            } catch (BankException e) {
                System.out.println("\nERROR : " + e.getMessage());
            }
        }
        scanner.close();
    }
}
