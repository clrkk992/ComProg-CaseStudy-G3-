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
        System.out.println("\n=========================================\n");
        System.out.println("   Welcome to the E-library Service!!!");
        System.out.println("\n=========================================\n");
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
                input.nextLine();
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
        } while (choice < 1 || choice > 6 || error == true);
    }

    static void viewRecord() {
        System.out.println("\n========================================\n");
        System.out.println("Transcript of all books: \n");

        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            int ctr = 1;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",\\s*");
                if (!line.isBlank()) {
                    System.out.println(ctr + ")");
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    System.out.println();
                    ctr++;
                }
            } 
        } catch (IOException e) {
            System.out.println("Error in viewing Records");
        }

        backToMain();
        System.out.println();
        return;
    } // displays all books

    static void findRecord() {

        char tryAgain = 'Y';
        do {
            System.out.println("\n========================================\n");
            System.out.println("Search Book by: ");
            System.out.println("[1] ISBN");
            System.out.println("[2] Title");
            System.out.println("[3] Author");
            System.out.println("[4] Return to Main Menu");
            boolean error;
            int choice = 0;

            do { // exception handling
                error = false;

                try {
                    System.out.println();
                    System.out.print("Enter a Choice: ");
                    choice = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) { // makes sure numbers lng ang input
                    System.out.print("\nPlease Enter a number from the choices (1-4) ");
                    error = true;
                    input.nextLine();
                }

                switch (choice) {   
                    case 1: searchISBN(); break;  
                    case 2: searchTitle(); break; 
                    case 3: searchAuthor(); break; 
                    case 4: displayMain(); break; 
                    default:
                        if (!error)
                            System.out.print("\nPlease Enter a correct choice (1-4)");
                }
            } while (choice < 1 || choice > 4 || error);

            //ask again
            System.out.println();
            tryAgain = confirmationPrompt("Do you want to Search again? ");

        } while (tryAgain == 'Y');

        displayMain(); 
        return;
    }
    
    static void searchISBN() {
        System.out.println("\n========================================\n");
        System.out.print("Enter a Book's ISBN to search: "); 
        String ISBN = input.nextLine(); 

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { 
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

            if(!found) 
                System.out.println("No books with this ISBN was found."); 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records."); 
        }  
    }
    
    static void searchAuthor() {
        System.out.println("\n========================================\n");
        System.out.print("Enter Author to search a Book: "); 
        String author = input.nextLine(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { 
            String line; 
            boolean found = false; 
            int ctr = 1;
            while((line = br.readLine()) != null) { 
                String[] data = line.split(",\\s*");
                if(data[2].equalsIgnoreCase(author)) { 
                    System.out.println();
                    System.out.println("Books Found by: " + author); // author can have more than 1 book
                    System.out.println();
                    System.out.println(ctr + ")");
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    ctr++;
                    found = true; 
                }  
            } 

            if(!found) 
                System.out.println("No book by this author was found."); 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records.");
        } 
    }

    static void searchTitle() {
        System.out.println("\n========================================\n");
        System.out.print("Enter Title to search a Book: "); 
        String title = input.nextLine(); 
        System.out.println();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { 
            String line; 
            boolean printed = false;
            boolean match = false;
            boolean exact = false;
            int ctr = 1;

            while ((line = br.readLine()) != null) { 
                String[] data = line.split(",\\s*");
                String bookTitle = data[1];

                if (bookTitle.equalsIgnoreCase(title)) {
                    System.out.println();
                    System.out.println("Book Found: ");
                    System.out.println();
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    exact = true;

                } else if (bookTitle.toLowerCase().contains(title.toLowerCase())) {

                    if (!printed) {
                    System.out.println("Book Title Matches found:");
                        printed = true;
                    }

                    System.out.println("\n" + ctr + ")");
                    System.out.println("Book Title  : " + data[1]);
                    System.out.println("Author      : " + data[2]);
                    System.out.println("ISBN        : " + data[0]);
                    System.out.println("Genre       : " + data[3]);
                    System.out.println("Availability: " + data[4]);
                    match = true;
                    ctr++;
                } 
            } 
            
            if (!exact && !match) {
                System.out.println("No books with this title were found.");
            } 

        } catch(IOException e) { 
            System.out.println("Error: Unable to access the book records."); 
        } 
    }

    static void addRecord() {
        System.out.println("\n========================================\n");

        char tryAgain = 'Y';

        do {
            try (FileWriter fw = new FileWriter("books.txt", true)) {
                System.out.println("Fill out the following\n");
    
                String title = errorBlank("Book Title");
                String ISBN = duplicateISBN();
                String author = errorBlank("Author");
                String genre = errorBlank("Genre");

                boolean availability = errorAvailability();

                // Confirmation
                char save = 'Y';
                do {
                    save = confirmationPrompt("SAVE the record?");

                    switch (save) {
                        case 'Y': {
                            BookConstructor newbook = new BookConstructor(ISBN, title, author, genre, availability);
                            Books.add(newbook);
                            fw.write(newbook.displayBook());
                            System.out.println("\nRecord succesfully added."); 
                            break;
                        }
                        case 'N': System.out.println("\nRecord addition cancelled."); break;
                        default: System.out.println("\nEnter a correct choice (Y/N)");
                    }
                } while (save != 'Y' && save != 'N');
                

            } catch (IOException e) {
                System.out.println("Error Adding Record");
            }

            tryAgain = confirmationPrompt("Add another record?");

            System.out.println();

        } while (tryAgain == 'Y');

        input.nextLine();
        backToMain();
        System.out.println();
        return;
    }

    static void editRecord() {
        System.out.println("\n========================================\n");
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            String targetISBN = findISBN();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(data[0].equals(targetISBN)) {
                    System.out.println("\nFill out the following: ");

                    String title = errorBlank("Book Title");
                    String ISBN = duplicateISBN();
                    String author = errorBlank("Author");
                    String genre = errorBlank("Genre");
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
                save = confirmationPrompt("SAVE the changes?");

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
        backToMain();
        System.out.println();
    }

    static void deleteRecord() {
        System.out.println("\n========================================\n");

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
                delete = confirmationPrompt("DELETE this record?");

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
        backToMain();
        System.out.println();
    }


    static void backToMain() {
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
                System.out.println("\nDuplicate ISBN! Please try again");
            }

            if (ISBN.isBlank())
                System.out.println("\nISBN cannot be blank. Please try again");

        } while(duplicate || ISBN.isBlank());

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
                System.out.println("\nISBN doesn't exist. Please try again");
            }

            if (ISBN.isBlank())
                System.out.println("\nISBN cannot be blank. Please try again");

        } while(!found || ISBN.isBlank());

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

    static String errorBlank(String name) {
        String var;

        do {
            System.out.print(name + ": ");
            var = input.nextLine();
            if (var.isBlank()) {
                System.out.println("\n" + name + " cannot be blank. Please try again.");
            }
        } while (var.isBlank());

        return var;
    }

    static char confirmationPrompt(String text) { // makes sure that the input is only Y or N
        char choice = 'Y';
        boolean error;
        
        do {
            error = false;
            try {
                System.out.print(text + " (Y/N): ");
                choice = input.next().charAt(0);
                choice = Character.toUpperCase(choice);
            } catch (InputMismatchException e) {
                System.out.println("Enter only Y/N");
                error = true;
                input.nextLine();
            }
        } while (error); 

        return choice;
    }
}

// 1) palitan ang printlns with \n(done)
// 2) Author can have more than 1 book(done)
// 3) Need ng mga comment symbols for whole program// end of method...
// 4) May space na na add sa books.txt when adding books
// 5)  Adding/editing accepts blanks lines as values, may ginawa ako kaso baka may better solution
// 6) Can genre/authors contain numbers?
// 7) magulo pa title matches, since lumilitaw parin yung matches kahit may excat match na
// 8) kapag nagkamali ka ng search searchauthor, searchISBN, and searchTitle, bumabalik sa choice uli ng search book imbes na magreenter sila?
// 9) Tumatanggap ng blank spaces ang mga nasa findrecord
// 10) Sa editing, You cannot reuse the ISBN, for example gusto mo baguhin lahat except sa ISBN hindi pwede kasi may duplicate ISBN

/* 
class LibraryBookInventorySystem {
    static Scanner input = new Scanner(System.in);
    static ArrayList<BookConstructor> Books = new ArrayList<>();
    static final String FILE_NAME = "books.txt";

    public static void main(String G3[]) {
        data();
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
        while (choice <1 || choice > 6) {
            System.out.print("Choice: ");
            choice = input.nextInt();
            System.out.println();
            switch (choice) {
                case 1: viewRecord(); break; // kean
                case 2: findRecord(); break; // grace
                case 3: addRecord(); break; // Lai
                case 4: editRecord(); break; // clark
                case 5: deleteRecord(); break; // nina
                case 6: System.exit(0); break;
                default: 
                System.out.println("Enter a correct choice: ");
            } 
        }
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
            int count = 1;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    System.out.println(count + ". " + line);
                    count++;
                }
            } 
            br.close();
        } catch (IOException e) {
            System.out.println("Error in viewing Records");
        }

        askUser("Press Enter To Return To Main: ");
        System.out.println();

    } // displays all books

    static void findRecord() {

    }

    static void addRecord() {
        input.nextLine();
        System.out.println("========================================");
        System.out.println();
        try {
        FileWriter fw = new FileWriter("books.txt", true);

        System.out.println("Fill out the following: ");
        System.out.print("Book Title: ");
        String title = input.nextLine();
        System.out.print("ISBN: ");
        String ISBN = input.nextLine();
        System.out.print("Author: ");
        String author = input.nextLine();
        System.out.print("Genre: ");
        String genre = input.nextLine();
        System.out.print("Availability (true/false): ");
        boolean availability = input.nextBoolean();

        BookConstructor newbook = new BookConstructor(ISBN, title, author, genre, availability);
        Books.add(newbook);
        fw.write(newbook.displayBook() + "\n");
        fw.close();
        
        System.out.println();
        System.out.println("Record Succcesfully Added");
        System.out.println();

        } catch (IOException e) {
            System.out.println("Error Adding Record");
        }

        askUser("Press Enter To Return To Main: ");
        System.out.println();

    }

    static void editRecord() {

    }

    static void deleteRecord() {

        //code mo


        //dito sa dulo mag add ka ng displayMain() as a temporary solution para magdisplay muna yung main
        
    }

    static void data() {
        //                ISBN -- Title -- Author -- Genre -- Availability
        try {
        FileWriter fw = new FileWriter("books.txt", true);
        BookConstructor book1 = new BookConstructor("ABC","Julian's Chronicles", "Kean", "Adventure", true);
        Books.add(book1);
        fw.write(book1.displayBook() + "\n");
        BookConstructor book2 = new BookConstructor("GHI","Mars And Charles and julia", "Taylor", "Documentary", false);
        Books.add(book2);
        fw.write(book2.displayBook() + "\n");
        BookConstructor book3 = new BookConstructor("DEF","Dalawa ang Daddy", "Owen", "Fiction", true);
        Books.add(book3);
        fw.write(book3.displayBook() + "\n");
        BookConstructor book4 = new BookConstructor("GHL","Tatlo ang itlog ni Julian", "Michael", "Reality Show", false);
        Books.add(book4);
        fw.write(book4.displayBook() + "\n");
        BookConstructor book5 = new BookConstructor("ABD","Ang ahas", "Mary", "Mystery", true);
        Books.add(book5);
        fw.write(book5.displayBook() + "\n");
        BookConstructor book6 = new BookConstructor("Gh2","Unconditionally", "Maragret Poppy", "Romance", true);
        Books.add(book6);
        fw.write(book6.displayBook() + "\n");
        BookConstructor book7 = new BookConstructor("AKD","Sun Eater", "Apollo", "Adventure", false);
        Books.add(book7);
        fw.write(book7.displayBook() + "\n");
        fw.close();
        } catch (IOException e) {
            System.out.println("Error Adding Record");
        }
        // SUggestion Sorting by Order asce/descd.
    } // declares value

    static String askUser(String question) {
    String answer = "";
    do {
        System.out.print(question);
        answer = input.nextLine();
        if(answer.isBlank()) {
            displayMain();
        } else {
            System.out.println("Please Prease Enter to Return to Main");
            System.out.println("");
        }
        return answer;
    } while (!answer.isBlank());
    }

    // Task:
    // Array -> temporary search result storage
    //Case insensitive title search
    //Partial keyword matching

    // Search by: 
        //ISBN
        //Title keyword
        //Author

    //confirmation prompts
        //SAVE the record?
        //SAVE the changes?
        //DELETE this record?

     //input validation// exemption handling
        // Duplicate ISBN 
        // Invalid input (needs loops)

    //add record/delete record
        //add/delete another record?
    
    //suggestions:
    //sorting by ascending/descending
    //Case senstive ba ISBN? is abc == ABC?



} */