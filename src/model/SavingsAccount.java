package model;
import java.lang.Override;

public class SavingsAccount extends Account {
    private double interestRate;
    public SavingsAccount(long accountNumber, String customerName, int pin, double balance) {
        super(accountNumber, customerName, pin, balance);
        this.interestRate = 5.0;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void accountFeatures(){
        System.out.println("Interest Rate : " + interestRate + "%");
    }
}
