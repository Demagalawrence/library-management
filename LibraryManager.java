// Starting point of LibraryManager
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManager {
     private ArrayList<Book> books;
    private Scanner scanner;

    public LibraryManager() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        manager.run();
    }

    public void run() {
        System.out.println("=== Library Management System ===");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayAllBooks();
                    break;
                case 3:
                    searchBookByTitle();
                    break;
                case 4:

                    removeBook(); // ✅ new feature
                    break;
                case 5:
                    System.out.println("Thank you for using Library Management System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Add New Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Search Book by Title");
        System.out.println("4. Exit");
    }

    private void addBook() {
        System.out.println("\n=== Add New Book ===");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        int year = getIntInput("Enter publication year: ");
        
        Book book = new Book(title, author, year);
        books.add(book);
        
        System.out.println("Book added successfully!");
    }

    private void displayAllBooks() {
        System.out.println("\n=== All Books in Library ===");
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    private void searchBookByTitle() {
        System.out.println("\n=== Search Book by Title ===");
        System.out.print("Enter title to search: ");
        String searchTitle = scanner.nextLine().toLowerCase();
        
        boolean found = false;
        System.out.println("Search Results:");
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                System.out.println(book);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books found with title containing: " + searchTitle);
        }
    }

    private void removeBook() {
        System.out.println("\n=== Remove Book ===");
        System.out.print("Enter title of the book to remove: ");
        String title = scanner.nextLine().toLowerCase();

        boolean removed = books.removeIf(book -> book.getTitle().toLowerCase().equals(title));

        if (removed) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("No book found with that title.");
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid numbers.");
            }
        }
    }
}

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("Title: %-30s | Author: %-20s | Year: %d", 
                           title, author, year);
    }
    
}
