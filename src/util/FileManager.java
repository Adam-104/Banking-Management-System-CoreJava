package util;

import model.Account;
import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "accounts.dat";

    @SuppressWarnings("unchecked")
    public static ArrayList<Account> loadAccounts(){
        File file = new File(FILE_NAME);

        if(!file.exists()){
            return new ArrayList<>();
        }

        try(ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME))) {
            return (ArrayList<Account>) input.readObject();
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Unable to load accounts.");
            return new ArrayList<>();
        }
    }

    public static void saveAccounts(ArrayList<Account> accounts){
        try(ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME))){
            output.writeObject(accounts);
        } catch(IOException e){
            System.out.println("Unable to save accounts.");
        }
    }
}
