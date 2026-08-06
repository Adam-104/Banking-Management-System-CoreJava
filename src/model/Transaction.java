package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {
    public static final long serialVersionUID = 1L;
    private String type;
    private double amount;
    private LocalDateTime transactionTime;
    private String remarks;

    public Transaction(String type, double amount, String remarks) {
        this.type = type;
        this.amount = amount;
        this.remarks = remarks;
        this.transactionTime = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getRemarks() {return remarks;}

    public LocalDateTime getTransactionTime() {return transactionTime;}

    public String getFormattedDateTime(){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        return transactionTime.format(formatter);
    }

    @Override
    public String toString() {
        return "\n---------------------------------"
                + "\nDate & Time : " + getFormattedDateTime()
                + "\nType        : " + type
                + "\nAmount      : ₹" + amount
                + "\nRemarks     : " + remarks
                + "\n---------------------------------";
    }
}
