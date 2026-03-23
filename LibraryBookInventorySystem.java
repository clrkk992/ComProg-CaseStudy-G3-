import java.io.*;
import java.util.*;

class LibraryBookInventorySystem {
    static Scanner input = new Scanner(System.in);
    static ArrayList<BookConstructor> Books = new ArrayList<>();
    static final String FILE_NAME = "books.txt";

    public static void main(String G3[]) {
        displayMain();
    }

    static void displayMain() {
        System.out.println("========================================");
        System.out.println();
        System.out.println("Welcome to the E-library Service!!!");
        System.out.println("[1] View all Record");
        System.out.println("[2] Find a Record");
        System.out.println("[3] Add a Record");
        System.out.println("[4] Edit a Record");
        System.out.println("[5] Delete a Record");
        System.out.println("[6] Exit");
        System.out.println();

        int choice = 0;
        boolean error;

        do { // exception handling
            error = false;

            try {
                System.out.print("Choice: ");
                choice = input.nextInt();
            } catch (InputMismatchException e) { // makes sure numbers lng ang input
                System.out.println("\nEnter only a number");
                error = true;
                input.nextLine();
            }

            switch (choice) {
                case 1:  viewRecord(); break;
                case 2:  findRecord(); break;
                case 3:  addRecord(); break;
                case 4:  editRecord(); break;
                case 5:  deleteRecord(); break;
                case 6:  System.out.println("\nThank you for using the E-library Service! Come back again!"); System.exit(0); break;
                default:
                    if (!error)
                        System.out.println("\nEnter a correct choice (1-6)");
            } 
        } while(choice < 1 || choice > 6 || error == true);
    }

    static void viewRecord() {
        input.nextLine();
        System.out.println("========================================");
        System.out.println();
        System.out.println("Transcript of all books: ");
        System.out.println();

        try {
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",\\s*");
                if (!line.isBlank()) {
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    System.out.println();
                }
            } 
            br.close();
        } catch (IOException e) {
            System.out.println("Error in viewing Records");
        }

        askUser();
        System.out.println();
    } // displays all books

    static void findRecord() {
        input.nextLine();
        boolean error;
        int sChoice = 0;

        boolean searching = true;

        while(searching) {
            System.out.println("\nSearch Book by: ");
            System.out.println("[1] ISBN");
            System.out.println("[2] Title");
            System.out.println("[3] Author");
            System.out.println("[4] Return to Main Menu");

            do { // exception handling
                error = false;

                try {
                    System.out.print("Choice: ");
                    sChoice = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) { // makes sure numbers lng ang input
                    System.out.println("\nEnter only a number");
                    error = true;
                    input.nextLine();
                }

                switch (sChoice) {   
                    case 1: searchISBN(); break;  
                    case 2: searchTitle(); break; 
                    case 3: searchAuthor(); break; 
                    case 4: searching = false; break; 
                    default:
                        if (!error)
                            System.out.println("\nEnter a correct choice (1-6)");
                }
            } while(error);

            if (searching) {
                System.out.print("\nDo you want to search again? (Y/N): ");
                String again = input.nextLine().trim();
                if (!again.equalsIgnoreCase("y")) {
                    searching = false; 
                }
            }
        }

        displayMain();    //if the user choose 4 and press n
    }

    static void searchISBN() {
        System.out.print("\nEnter ISBN to search a Book: "); 
        String ISBN = input.nextLine(); 

        try{ 
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME)); 
            String line; 
            boolean found = false; 

            while((line = br.readLine()) != null) { 
                String[] data = line.split(",\\s*"); 
                if(data[0].equals(ISBN)) { // case sensitive in ISBN
                    System.out.println();
                    System.out.println("Book Found");
                    System.out.println();
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    found = true; 
                    break;
                } 
            } 

            br.close(); 

            if(!found) 
                System.out.println("No books with this ISBN was found."); 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records."); 
        }  
    }
   
    static void searchAuthor() {
        System.out.print("\nEnter Author to search a Book: "); 
        String author = input.nextLine(); 
        
        try{ 
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME)); 
            String line; 
            boolean found = false; 

            while((line = br.readLine()) != null) { 
                String[] data = line.split(",\\s*"); 
                if(data[2].equalsIgnoreCase(author)) { 
                    System.out.println();
                    System.out.println("Book Found");
                    System.out.println();
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    found = true; 
                    break;
                }   
            } 
            br.close(); 

            if(!found) 
                System.out.println("No book by this author was found."); 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records.");
        } 
    }

    static void searchTitle() {
        System.out.print("\nEnter Title to search a Book: "); 
        String title = input.nextLine(); 
        
        try { 
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME)); 
            String line; 
            Set<String> results = new LinkedHashSet<>();

            while((line = br.readLine()) != null) { 
                String[] data = line.split(",\\s*");
                if (data.length > 1) {
                    String bookTitle = data[1];

                    if (bookTitle.equalsIgnoreCase(title)) {
                        System.out.println();
                        System.out.println("Book Found");
                        System.out.println("Book Title  : " + data[1]);
                        System.out.println("Author      : " + data[2]);
                        System.out.println("ISBN        : " + data[0]);
                        System.out.println("Genre       : " + data[3]);
                        System.out.println("Availability: " + data[4]);
                        break;} //------------------------------------- stop after match
                    //-------------------------------------------------- Case-insensitive keyword search
                    else if (bookTitle.toLowerCase().contains(title.toLowerCase())) {
                        results.add("Book Title  : " + data[1]);
                        results.add("Author      : " + data[2]);
                        results.add("ISBN        : " + data[0]);
                        results.add("Genre       : " + data[3]);
                        results.add("Availability: " + data[4]);
                    }
                }
            }  
            
            br.close();

            if (!results.isEmpty()) {
                System.out.println();
                System.out.println("Book Title Matches found:");
                int ct = 0;
                for (String result : results) {
                    if (ct % 5 == 0)
                        System.out.println();

                    System.out.println(result);
                    ct++;
                }
            }

            else {
                System.out.println("No books with this title were found.");
            } 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records."); 
        } 
    }

    static void addRecord() {
        input.nextLine();
        System.out.println("========================================");
        System.out.println();

        char choice = 'Y';

        do {
            try {
                BufferedReader br = new BufferedReader(new FileReader("books.txt"));
                FileWriter fw = new FileWriter("books.txt", true);

                System.out.println("Fill out the following: ");

                System.out.print("Book Title: ");
                String title = input.nextLine();

                String ISBN = duplicateISBN();

                System.out.print("Author: ");
                String author = input.nextLine();

                System.out.print("Genre: ");
                String genre = input.nextLine();

                boolean availability = errorAvailability();

                // Confirmation
                char save = 'Y';
                do {
                    save = errorConfirmation("SAVE the record?");

                    switch (save) {
                        case 'Y': {
                            BookConstructor newbook = new BookConstructor(ISBN, title, author, genre, availability);
                            Books.add(newbook);
                            fw.write(newbook.displayBook() + "\n");
                            System.out.println("\nRecord succesfully added."); 
                            break;
                        }
                        case 'N': System.out.println("\nRecord addition cancelled."); break;
                        default: System.out.println("\nEnter a correct choice (Y/N)");
                    }
                } while (save != 'Y' && save != 'N');
                
                br.close();
                fw.close();

            } catch (IOException e) {
                System.out.println("Error Adding Record");
            }

            choice = errorConfirmation("Add another record?");

            System.out.println();

        } while (choice == 'Y');

        askUser();
        System.out.println();
    }

    static void editRecord() {
        input.nextLine();

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String line;
            
            String targetISBN = findISBN();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(data[0].equals(targetISBN)) {
                    System.out.println("\nFill out the following: ");

                    System.out.print("Book Title: ");
                    String title = input.nextLine();

                    String ISBN = duplicateISBN();

                    System.out.print("Author: ");
                    String author = input.nextLine();

                    System.out.print("Genre: ");
                    String genre = input.nextLine();
                    
                    boolean availability = errorAvailability();

                    BookConstructor newbook = new BookConstructor(ISBN, title, author, genre, availability);
                    Books.add(newbook);
                    bw.write(newbook.displayBook() + "\n");
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }   

            br.close();
            bw.close();

            // Confirmation
            char save = 'Y';
            do {
                save = errorConfirmation("SAVE the changes?");

                switch (save) {
                    case 'Y': {
                        inputFile.delete();
                        tempFile.renameTo(inputFile);
                        System.out.println("\nRecord succesfully updated.\n"); 
                        break;
                    }
                    case 'N': tempFile.delete(); System.out.println("\nRecord edit cancelled.\n"); break;
                    default: System.out.println("\nEnter a correct choice (Y/N)");
                }
            } while (save != 'Y' && save != 'N');
            

        } catch(IOException e) {
            System.out.println("\nError editing record");
        }

        input.nextLine();
        askUser();
        System.out.println();
    }

    static void deleteRecord() {
        input.nextLine();  

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String line;

            String isbnToDelete = findISBN();
            
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] data = line.split(",");
                    String isbn = data[0].trim();
                    if (!isbn.equalsIgnoreCase(isbnToDelete)) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }

            br.close();
            bw.close();

            // Confirmation
            char delete = 'Y';
            do {
                delete = errorConfirmation("DELETE this record?");

                switch (delete) {
                    case 'Y': {
                        inputFile.delete();
                        tempFile.renameTo(inputFile);
                        System.out.println("\nRecord succesfully deleted.\n"); 
                        break;
                    }
                    case 'N': tempFile.delete(); System.out.println("\nRecord deleting cancelled.\n"); break;
                    default: System.out.println("\nEnter a correct choice (Y/N)");
                }
            } while (delete != 'Y' && delete != 'N');

        } catch (IOException e) {
            System.out.println("Error deleting record");
        }

        input.nextLine();
        askUser();
        System.out.println();
    }

    static void askUser() {
        String answer = "";
        do {
            System.out.print("Press Enter to return to Main: ");
            answer = input.nextLine();

            if(answer.isBlank()) {
                displayMain();
            } else {
                System.out.println();
                System.out.println("Please press Enter only");
            }
        } while (!answer.isBlank());
    }

    static String duplicateISBN() { // for duplication of ISBN
        boolean duplicate;
        String line;
        String ISBN;

        do { // loop para mahanap ang ISBN
            duplicate = false;

            System.out.print("ISBN: ");
            ISBN = input.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) { // para basahin niya ulit ung buong file
                while((line = reader.readLine()) != null) {
                    String[] data = line.split(",\\s*");
                    if (data[0].equals(ISBN)) {
                        duplicate = true; // ISBN found in the txt file
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }

            if (duplicate) {
                System.out.println();
                System.out.println("Duplicate ISBN! Please try again");
            }
        } while(duplicate);

        return ISBN;
    }

    static String findISBN() { // for finding an ISBN in the txt file
        boolean found;
        String line;
        String ISBN;

        do { // loop para mahanap ang ISBN
            found = false;

            System.out.print("Enter ISBN to edit: ");
            ISBN = input.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { // para basahin niya ulit ung buong file
                while((line = reader.readLine()) != null) {
                    String[] data = line.split(",\\s*");
                    if (data[0].equals(ISBN)) {
                        found = true; // ISBN found in the txt file
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }

            if (!found) {
                System.out.println();
                System.out.println("ISBN doesn't exist. Please try again");
            }
        } while(!found);

        return ISBN;
    }

    static boolean errorAvailability() { // if the user's input is not true or false
        boolean availability = true;
                
        boolean error; // for exception handling
        do {
            error = false;
            try {
                System.out.print("Availability (true/false): ");
                availability = input.nextBoolean();
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Must be true or false only");
                error = true;
                input.nextLine();
            }
        } while(error);

        return availability;
    }

    static char errorConfirmation(String text) { // makes sure that the input is only Y or N
        char choice = 'Y';
        boolean error;
        
        do {
            error = false;
            try {
                System.out.print(text + " (Y/N): ");
                choice = input.next().charAt(0);
                choice = Character.toUpperCase(choice);
            } catch(InputMismatchException e) {
                System.out.println("Enter only Y/N");
                error = true;
                input.nextLine();
            }
        } while (error); 

        return choice;
    }
}
