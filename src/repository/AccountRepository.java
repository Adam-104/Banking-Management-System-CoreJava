package repository;

import model.Account;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountRepository {
    private final ArrayList<Account> accounts;

    private final Random random = new Random();


    public AccountRepository() {
        accounts = FileManager.loadAccounts();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        FileManager.saveAccounts(accounts);
    }

    public Account findByAccountNumber(long accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public List<Account> findByCustomerName(String customerName) {
        List<Account> matchedAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getCustomerName()
                    .toLowerCase()
                    .contains(customerName.toLowerCase())) {
                matchedAccounts.add(account);
            }
        }

        return matchedAccounts;
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void updateAccounts() {
        FileManager.saveAccounts(accounts);
    }

    public boolean deleteAccount(Account account) {
        boolean removed = accounts.remove(account);

        if (removed) {
            FileManager.saveAccounts(accounts);
        }
        return removed;
    }

    public int getTotalCustomers() {
        return accounts.size();
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public long generateUniqueAccountNumber() {
        long accountNumber;
        do {
            accountNumber = 100000000000L
                    + (long) (random.nextDouble() * 900000000000L);
        } while (findByAccountNumber(accountNumber) != null);
        return accountNumber;
    }
}
