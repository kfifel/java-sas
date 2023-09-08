package controller;

import model.Book;
import security.Authentication;
import service.*;

import java.sql.SQLException;
import java.util.*;

public class MenuController {
    private BookService bookService;
    private ReportService reportService;
    private BorrowService borrowService;
    private Scanner sc;
    private List<Book> books;

    private MenuController () {}

    public MenuController(BookService bookService, ReportService reportService, BorrowService borrowService) {
        this.bookService = bookService;
        this.reportService = reportService;
        this.borrowService = borrowService;
        sc = new Scanner(System.in);
        books = new ArrayList<>();
    }

    public void start() throws SQLException {
        if (Authentication.librarian == null){
            this.login(0);
        }
        int input;

        while(true){
            PrintingService.printMenue();
            input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1: this.addNewBook();              break;
                case 2: this.displayAvailableBooks();   break;
                case 3: this.searchForBook();           break;
                case 4: this.returnBook();              break;
                case 5: this.listBorrowedBooks();       break;
                case 6: this.reportService.generateRapport(); break;
                case 0:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");

            }
        }
    }

    private void login(int trys) throws SQLException {
        if(trys == 3){
            System.out.println("Your trys limit is finished");
            System.exit(0);
        }
        System.out.println("=====================================");
        System.out.println("============ LOGIN ==================");
        System.out.println("=====================================");
        System.out.println(" ");
        System.out.print("====  EMAIL: ");
        String email = sc.nextLine();
        System.out.print("====  PASSWORD: ");
        String password = sc.nextLine();
        if (!Authentication.login(email, password)){
            login(++trys);
        }
    }

    private void addNewBook() throws SQLException {
        Book newBook = PrintingService.getBookFromLibrarian();

        Authentication.librarian.addBookAdded(
                bookService.save(newBook));
    }

    private void displayAvailableBooks() throws SQLException {
        books = bookService.read();
        PrintingService.printBookTable(books);
        int input = PrintingService.printBookOptions();

        switch (input) {
            case 1: this.addNewBook();break;
            case 2: this.modifyBookInformation();break;
            case 3: this.deleteBook();break;
            case 4: this.borrowBook();break;
            case 5: this.searchForBook();break;
            default:break;
        }
    }

    private void borrowBook() {
        String isbn = PrintingService.getIsbnFromLibrarian();


    }

    private Book searchForBook() throws SQLException {
        System.out.println("+----------------------------------------------+");
        System.out.println("|--------------- RECHERCHER UN LIVRE ----------|");
        System.out.println("+----------------------------------------------+");
        String isbn;
        do {
            System.out.print("+-----DONNER ISBN DE LIVRE:    ");
            isbn = sc.nextLine();

        }while(isbn.isEmpty());

        Book book = bookService.findByIsbn(isbn);

        if (book == null)
            System.out.println("Aucune livre trouvé !");
        else
            PrintingService.printBookTable(Collections.singletonList(book));

        return book;
    }

    private void returnBook() {
    }

    private void listBorrowedBooks() {
    }

    private void deleteBook() throws SQLException {
        String isbn = PrintingService.getIsbnFromLibrarian();
        Book book;
        book = bookService.findByIsbn(isbn);

        if (book == null)
            System.out.println("book n'est pas trouvé!!");
        else {
            if(bookService.delete(book))
                System.out.println("le livre a étais bien supprimer");
        }
    }

    private void modifyBookInformation() throws SQLException {
        Book book = this.searchForBook();
        Book newBook = PrintingService.getBookFromLibrarian();

        book.setTitre(newBook.getTitre());
        book.setDescription(newBook.getDescription());
        book.setAuthor(newBook.getAuthor());
        book.setQuantity(newBook.getQuantity());

        bookService.update(book);
    }

    private void generateReport() {
        reportService.generateRapport();
    }
}
