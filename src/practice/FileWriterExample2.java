package practice;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterExample2 {
    public static void main(String[] args) throws IOException {
        try {
            FileWriter writer = new FileWriter("bank.txt", true);
            writer.write("Account Number : 101\n");
            writer.write("Name           : Adam\n");
            writer.write("Account Type   : Savings\n");
            writer.write("Balance        : 5000");
            writer.close();
            System.out.println("101 details are written int file");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
