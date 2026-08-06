package practice;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class BufferedWriterExample1 {
    public static void main(String[] args) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter("customer.txt");
            fileWriter.write("Welcome\n");
            fileWriter.write("to\n");
            fileWriter.write("Bank\n");
            fileWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new FileReader("customer.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
