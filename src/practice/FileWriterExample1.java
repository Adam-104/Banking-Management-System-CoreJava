package practice;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterExample1 {
    public static void main(String[] args) throws IOException {
        try {
            FileWriter writer = new FileWriter("bank.txt", true);
            writer.write("Hello World\n");
            writer.write("this is second sentence\n");
            writer.write("this is third sentence\n");
            writer.close();
            System.out.println("Successfully wrote to the file");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
