package practice;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterExample3 {
    public static void main(String[] args) throws IOException {
        try {
            FileWriter writer = new FileWriter("customer.txt", true);
            writer.write("Account Number : 101\n");
            writer.flush();
            writer.write("Name           : Adam\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }
}
