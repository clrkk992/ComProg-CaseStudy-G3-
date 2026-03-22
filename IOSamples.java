import java.io.*;
import java.util.*;

class IOSamples {
    static Scanner input = new Scanner(System.in);

    public static void main(String G3[]) {
        System.out.println("Welcome to the Library Book Inventory System!");

        //samples lang to ng mga ginawa ni sir, pwede kayo mag base here while gumagawa

        //File creation
        try {
            File FILE_NAME = new File("books.txt");
            if (FILE_NAME.createNewFile()) {
                System.out.println("File succesfully created: " + FILE_NAME.getName());
            } else {
                System.out.println("File already exist");
            }
            System.out.println(FILE_NAME.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        //File writing
        try {
            FileWriter FILE_NAME = new FileWriter("books.txt");
            FILE_NAME.write("Jumbo");
            FILE_NAME.write("Hotdog");
            FILE_NAME.close();
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        //File reading
        try {
            FileReader FILE_NAME = new FileReader("books.txt");
            int character;
            while((character = FILE_NAME.read()) != -1) {
                System.out.print((char) character);
            }
            FILE_NAME.close();
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        
        //BufferedReader

        try {
            BufferedReader FILE_NAME = new BufferedReader(new FileReader("books.txt"));
            String line;

            while ((line = FILE_NAME.readLine()) != null) {
                System.out.println(line);
            }

            FILE_NAME.close();

        } catch(IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }



    }
}