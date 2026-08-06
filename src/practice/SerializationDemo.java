package practice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable {
    int id;
    String name;

    Student(int id, String name){
        this.id = id;
        this.name = name;
    }
}
public class SerializationDemo {
    public static void main(String[] args) throws IOException {
        Student student = new Student(101, "akon");

        try(ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream("student.dat"))){
            out.writeObject(student);

            System.out.println("Object Serialized Successfully");
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
