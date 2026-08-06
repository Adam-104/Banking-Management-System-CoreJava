package practice;
import java.io.File;
import java.io.IOException;

public class FileExample1 {
    public static void main(String[] args) throws IOException {
        File file = new File("bank.txt");
        System.out.println(file.exists());
        if (file.createNewFile()) {
            System.out.println("File created");
        }
        else  {
            System.out.println("File already exists");
        }
    }
}
