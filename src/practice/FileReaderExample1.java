package practice;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderExample1 {
    public static void main(String[] args) throws IOException {
        try {
            FileReader fileReader = new FileReader("customer.txt");
            int content;
            while ((content = fileReader.read()) != -1){
                System.out.print((char) content);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
