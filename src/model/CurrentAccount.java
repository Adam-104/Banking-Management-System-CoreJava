package model;
import java.lang.Override;

public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(long accountNumber, String customerName, int pin, double salary){
        super(accountNumber, customerName, pin, salary);
        this.overdraftLimit = 100000;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void accountFeatures(){
        System.out.println("Overdraft Limit : " + overdraftLimit);
    }
}
