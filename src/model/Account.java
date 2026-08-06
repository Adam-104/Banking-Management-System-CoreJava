package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.Account;

public abstract class Account implements Serializable{
    public static final long serialVersionUID = 1L;
    private long accountNumber;
    private String customerName;
    private  int pin;
    private double balance;

    private ArrayList<Transaction> transactions;

    public Account(long accountNumber, String customerName, int pin, double balance){
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.pin = pin;
        this.balance = balance;

        transactions = new ArrayList<>();
        transactions.add(new Transaction("Deposit", balance, "Intitial Deposit"));
    }

    public abstract void accountFeatures();

    public long getAccountNumber() {
        return accountNumber;
    }
    public String getCustomerName(){
        return customerName;
    }
    public int getPin(){
        return pin;
    }
    public double getBalance(){
        return balance;
    }
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setPin(int pin){
        this.pin = pin;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
}
