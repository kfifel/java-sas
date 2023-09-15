package controller;

import configuration.Const;
import ennumiration.BookBorrowStatus;
import entities.Book;
import entities.BookBorrow;
import entities.Borrower;
import entities.dto.StaticticsData;
import security.Authentication;
import service.*;

import java.sql.SQLException;
import java.util.*;

public class MenuController {
    private final BookService bookService;
    private final ReportService reportService;
    private final BorrowService borrowService;
    private final BorrowerService borrowerService;
    private final Scanner sc;
    private List<Book> books;

    public MenuController(BookService bookService, ReportService reportService, BorrowService borrowService, BorrowerService borrowerService) {
        this.bookService = bookService;
        this.reportService = reportService;
        this.borrowService = borrowService;
        this.borrowerService = borrowerService;

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
                case 6: this.generateReport();          break;
                case 7: Authentication.logout();
                        login(0);                  break;
                case 0:
                    ConsoleMessageService.info("Exiting Library Management System. Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    ConsoleMessageService.info("Invalid choice. Please enter a valid option.");

            }
        }
    }

    private void login(int trys) throws SQLException {
        if(trys == 3){
            ConsoleMessageService.error("Your trys limit is finished");
            System.exit(0);
        }
        System.out.println("=====================================");
        System.out.println("============ LOGIN ==================");
        System.out.println("=====================================");
        System.out.println(" ");
        System.out.print("====  EMAIL: ");
        String email = sc.nextLine().trim().toLowerCase();
        System.out.print("====  PASSWORD: ");
        String password = sc.nextLine().trim();
        if (!Authentication.login(email, password)){
            login(++trys);
        }
    }

    private void addNewBook() throws SQLException {
        Book newBook = PrintingService.getBookFromLibrarian();

        this.validate(newBook);

        bookService.save(newBook);
    }
    public boolean validate(Book book) {
        boolean isValid = true;

        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            ConsoleMessageService.warning("ISBN ne peut pas être vide.");
            isValid = false;
        }

        if (book.getTitre() == null || book.getTitre().isEmpty()) {
            ConsoleMessageService.warning("Le titre ne peut pas être vide.");
            isValid = false;
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            ConsoleMessageService.warning("L'auteur ne peut pas être vide.");
            isValid = false;
        }

        if (book.getQuantity() < 0) {
            ConsoleMessageService.warning("La quantité ne peut pas être négative.");
            isValid = false;
        }

        return isValid;
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

    private void borrowBook() throws SQLException {
        Book book = bookService.findByIsbn(PrintingService.getIsbnFromLibrarian());
        if(book == null) {
            ConsoleMessageService.info("book is not exist");
            return ;
        }

        if(book.getQuantity() <= 0) {
            ConsoleMessageService.warning("la quantité est insuffisante");
            return ;
        }

        ConsoleMessageService.info("1: Emprunteur déjà exister");
        ConsoleMessageService.info("2: Ajouter un nouveau emprunteur");

        int input = PrintingService.getIntFromUser();
        Borrower borrower;
        switch (input) {
            case 1: borrower = this.getExistsBorrowerFromUser(); break;
            case 2: borrower = this.getNewBorrowerFromUser(); break;
            default: return ;
        }

        Date dateReturned = PrintingService.getDateReturnFromUser();
        BookBorrow bookBorrow = new BookBorrow();

        bookBorrow.setBook(book);
        bookBorrow.setBorrower(borrower);
        bookBorrow.setReturn_date(dateReturned);
        bookBorrow.setStatus(BookBorrowStatus.TAKEN);

        if(borrowService.borrowBook(bookBorrow).getId() != 0)
            ConsoleMessageService.success("l'emprunt a étais bien enregistrer");
        else
            ConsoleMessageService.error("Error is occurred!");
    }

    private Borrower getNewBorrowerFromUser() {

        ConsoleMessageService.info("Entrez le nom complet du nouvel emprunteur :");
        String fullName = sc.nextLine().trim();
        Borrower borrower =  new Borrower(0, fullName, new Date());
        borrower = borrowerService.save(borrower);
        return borrower;
    }

    private Borrower getExistsBorrowerFromUser() {
            return this.salectBorrower();
    }

    private Borrower salectBorrower() {
        List<Borrower> borrowers = borrowerService.read();
        PrintingService.printBorrowers(borrowers);

        System.out.println("+----------------------------------------------+");
        System.out.println("+---------- SÉLECTIONNER L'EMPRUNTEUR ---------+");
        System.out.println("+----------------------------------------------+\n");

        System.out.print("+----- ID DE L'EMPRUNTEUR :");
        int id = sc.nextInt();

        Borrower borrower = borrowerService.findById(id);

        if (borrower == null)
            ConsoleMessageService.warning("Aucune emprunteur trouvé !");

        return borrower;
    }

    private Book searchForBook() {
        System.out.println("+----------------------------------------------+");
        System.out.println("|--------------- RECHERCHER UN LIVRE ----------|");
        System.out.println("+----------------------------------------------+");
        String isbn;
        do {
            System.out.print("+-----DONNER ISBN DE LIVRE:    ");
            isbn = sc.nextLine();

            if(isbn.isEmpty())
                ConsoleMessageService.warning("isbn couldn't be empty");

        }while(isbn.isEmpty());

        Book book = bookService.findByIsbn(isbn);

        if (book == null)
            ConsoleMessageService.warning("Aucune livre trouvé !");
        else
            PrintingService.printBookTable(Collections.singletonList(book));

        return book;
    }

    private void returnBook() {
        String isbn = PrintingService.getIsbnFromLibrarian();
        Book book = bookService.findByIsbn(isbn);
        if(book == null) {
            ConsoleMessageService.error("No book is found with isbn: " + isbn);
            return ;
        }
        Borrower borrower = salectBorrower();

        BookBorrow bookBorrow = borrowService.findBookBorrowByBorrowerIdAndIsbn(borrower, book);
        if (bookBorrow == null)
            System.out.println(Const.RED +
                    "l'emprunt avec le livre: " + Const.BLEU + book.getTitre() + Const.RED +
                    " et l'emprunteur : " + Const.WHITE + borrower.getFull_name() + Const.RED +
                    " n'existe pas!!"+Const.WHITE);
        else {
            PrintingService.printBorrowBooks(Collections.singletonList(bookBorrow));

            if (borrowService.returned(bookBorrow)) {
                ConsoleMessageService.success("livre bien returner!!!");
            } else {
                ConsoleMessageService.error("Error has occurred !!!");
            }
        }
    }

    private void listBorrowedBooks() {
        List<BookBorrow> bookBorrow = borrowService.read();
        PrintingService.printBorrowBooks(bookBorrow);

        System.out.println("1: RETURN | 2: UPDATE | 0: GO BACK ");
        int input = PrintingService.getIntFromUser();
        switch (input) {
            case 1: returnBook(); break;
            case 2: updateBookBorrow(); break;
            default: break;
        }
    }

    private void updateBookBorrow() {}

    private void deleteBook() throws SQLException {
        String isbn = PrintingService.getIsbnFromLibrarian();
        Book book;
        book = bookService.findByIsbn(isbn);

        if (book == null)
            ConsoleMessageService.error("book n'est pas trouvé!!");
        else {
            if(bookService.delete(book))
                ConsoleMessageService.info("le livre a étais bien supprimer");
        }
    }

    private void modifyBookInformation() throws SQLException {
        Book book = this.searchForBook();
        if(book == null){
            ConsoleMessageService.warning("There is no book with this isbn");
            return;
        }
        ConsoleMessageService.warning("\n****************** if column is not include for modification don't fill it *************\n");

        Book newBook = PrintingService.getBookFromLibrarian();

        if (newBook.getTitre() != null && !newBook.getTitre().isEmpty()) {
            book.setTitre(newBook.getTitre());
        }

        if (newBook.getDescription() != null && !newBook.getDescription().isEmpty()) {
            book.setDescription(newBook.getDescription());
        }

        if (newBook.getAuthor() != null && !newBook.getAuthor().isEmpty()) {
            book.setAuthor(newBook.getAuthor());
        }

        if (newBook.getQuantity() >= 0) {
            book.setQuantity(newBook.getQuantity());
        }

        if(bookService.update(book))
            ConsoleMessageService.success("Livre est bien modifier !");
        else
            ConsoleMessageService.error("Somme error is occurred !!");
    }

    private void generateReport() {
        StaticticsData staticticsData =  this.reportService.generateRapport();

        PrintingService.printStatistics(staticticsData);
    }
}
