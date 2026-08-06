package util;

import model.Account;

public class Menu {

    public static void showMainMenu() {
        System.out.println("\n============================================");
        System.out.println("           BANKING MANAGEMENT SYSTEM        ");
        System.out.println("==============================================");
        System.out.println("1. Create Customer Account");
        System.out.println("2. Customer Login");
        System.out.println("3. Admin Login");
        System.out.println("4. Exit");
        System.out.println("==============================================");
        System.out.print("Enter your choice : ");
    }

    public static void showCustomerMenu(Account account){
        System.out.println("\n============================================");
        System.out.println("        CUSTOMER DASHBOARD          ");
        System.out.println("==============================================");
        System.out.println("Customer Name   : " + account.getCustomerName());
        System.out.println("Account Number  : " + account.getAccountNumber());
        System.out.println("Account Type    : " + account.getClass().getSimpleName());
        System.out.printf("Balance        : ₹%.2f%n", account.getBalance());
        System.out.println("==============================================");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Transaction History");
        System.out.println("6. Mini Statement");
        System.out.println("7. Change PIN");
        System.out.println("8. Logout");
        System.out.println("==============================================");
        System.out.print("Enter your choice : ");
    }

    public static void showAdminMenu(){
        System.out.println("\n============================================");
        System.out.println("            ADMIN DASHBOARD          ");
        System.out.println("==============================================");
        System.out.println("1. Display All Accounts");
        System.out.println("2. Search By Account Number");
        System.out.println("3. Search By Customer Name");
        System.out.println("4. Delete Account");
        System.out.println("5. Total Customers");
        System.out.println("6. Total Bank Balance");
        System.out.println("7. Logout");
        System.out.println("==============================================");
        System.out.print("Enter your choice : ");
    }
}