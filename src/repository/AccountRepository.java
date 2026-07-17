package repository;
import model.Account;
import java.util.ArrayList;

public class AccountRepository {
    private ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(Account account){
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public Account findByAccountNumber(long accountNumber){
        for (Account account : accounts){
            if (account.getAccountNumber() == accountNumber){
                return account;
            }
        }
        return null;
    }
}
