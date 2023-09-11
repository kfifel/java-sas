package service;

import model.Book;
import model.BookBorrow;
import model.Borrower;
import model.dto.StaticticsData;
import repository.BorrowerRepository;
import security.Authentication;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.text.SimpleDateFormat;


public class PrintingService {

    public static int printBookOptions() {
        System.out.println("1: CREATE  | 2: UPDATE  | 3: DELETE  | 4: BORROW | 5: SEARCH | 0: GO BACK");
        System.out.print(": ");
        Scanner sc = new Scanner(System.in);
        int res = sc.nextInt();;
        return res;
    }

    public static Book getBookFromLibrarian() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN:");
        String isbn = scanner.nextLine();

        System.out.print("Enter Title:");
        String title = scanner.nextLine();

        System.out.print("Enter Description:");
        String description = scanner.nextLine();

        System.out.print("Enter Author:");
        String author = scanner.nextLine();

        System.out.print("Enter Quantity:");
        int quantity = scanner.nextInt();

        return new Book(isbn, title, description, author, quantity, Authentication.librarian, new Date());

    }

    public static Date getDateReturnFromUser() {
        Scanner sc = new Scanner(System.in);
        String userInput;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        do{
            try {
                System.out.print("Donner la date de return (format: yyyy-MM-dd): "); userInput = sc.nextLine();
                date = dateFormat.parse(userInput);
            } catch (ParseException e) {
                Logger.error("Invalid date format. Please use yyyy-MM-dd.");
            }
        }while(date == null);

        return date;
    }

    public static Borrower getBorrowerExists() {
        BorrowerService borrowerService = new BorrowerService();

        PrintingService.printBorrowers(borrowerService.read());
        System.out.println(" SELECT THE ID OF THE BORROWER :");
        int borrowerId = PrintingService.getIntFromUser();
        borrowerService.findById(borrowerId);
        return  PrintingService.getBorrowerFromLibrarian();

    }

    private static Borrower getBorrowerFromLibrarian() {
        return null;
    }

    public static int getIntFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("    :) ");
        return sc.nextInt();
    }


    public static void printBorrowers(List<Borrower> borrowers) {
        System.out.println("==================================================");
        System.out.println("============== PRINT BORROWERS ===================");
        System.out.println("==================================================");

        if (borrowers.isEmpty()) {
            System.out.println("No borrowers found.");
        } else {
            System.out.printf("%-5s %-30s %-20s%n", "ID", "Full Name", "Created At");
            System.out.println("--------------------------------------------------");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Borrower borrower : borrowers) {
                System.out.printf("%-5d %-30s %-20s%n",
                        borrower.getId(),
                        borrower.getFull_name(),
                        dateFormat.format(borrower.getCreated_at())
                );
            }
        }
    }

    public static void printBorrowBooks(List<BookBorrow> bookBorrows) {
        System.out.println("==================================================");
        System.out.println("============= PRINT BOOK BORROWERS ===============");
        System.out.println("==================================================");

        if (bookBorrows.isEmpty()) {
            System.out.println();
            System.out.println("No book borrows found.");
            System.out.println();
        } else {
            System.out.printf("|%-5s | %-35s | %-20s | %-20s | %-20s%n", "ID", "Book Title", "Status", "Borrow date", "Date Returned");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (BookBorrow bookBorrow : bookBorrows) {
                System.out.printf("|%-5d | %-35s | %-20s | %-20s | %-20s%n",
                        bookBorrow.getId(),
                        bookBorrow.getBook().getTitre(),
                        bookBorrow.getStatus(),
                        dateFormat.format(bookBorrow.getBorrow_date()),
                        dateFormat.format(bookBorrow.getReturn_date())
                );
                System.out.println("-----------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public static void printStatistics(StaticticsData statisticsData) {
        int lineLength = 36;
        String border =    "****************************************";
        String emptyLine = "*                                      *";

        System.out.println(border);
        System.out.println(emptyLine);
        System.out.println("*       Library Statistics Report      *");
        System.out.println(emptyLine);
        System.out.println(border );

        System.out.printf("* %-"+lineLength+"s *\n" , "Number of Books: " + statisticsData.BookCount);
        System.out.printf("* %-"+lineLength+"s *\n" ,"Number of Borrowers: " + statisticsData.BorrowerCount);
        System.out.println(emptyLine);
        System.out.printf("* %-"+lineLength+"s *\n" ,"Most Borrowed Book:");
        System.out.printf("* %-"+lineLength+"s *\n", "   ISBN: " + statisticsData.MostBorrow.getIsbn());
        System.out.printf("* %-"+lineLength+"s *\n", "   Title: " + statisticsData.MostBorrow.getTitre());
        System.out.println(emptyLine);
        System.out.printf("* %-"+lineLength+"s *\n", "Number of Borrows This Year: " + statisticsData.BorrowCountThisYear);

        System.out.println( border + "\n");
    }


    public void searchForBook() {
    }

    public void displayAvailableBooks() {

    }
    public void returnBook() {
    }

    public void listBorrowedBooks() {
    }

    public static String getIsbnFromLibrarian() {
        System.out.print("SAISEZ L'ISBN DE LIVRE : ");
        return new Scanner(System.in).nextLine();
    }

    public void modifyBookInformation() {
    }

    public void generateReport() {

    }
    public static void printLibraryManagement(){
        String text = " __       __  .______   .______          ___      .______     ____    ____    .___  ___.      ___      .__   __.      ___      .___  ___.   _______ .___  ___.  _______ .__   __. .___________.\n" +
                "|  |     |  | |   _  \\  |   _  \\        /   \\     |   _  \\    \\   \\  /   /    |   \\/   |     /   \\     |  \\ |  |     /   \\     |   \\/   |  /  _____||   \\/   | |   ____||  \\ |  | |           |\n" +
                "|  |     |  | |  |_)  | |  |_)  |      /  ^  \\    |  |_)  |    \\   \\/   /     |  \\  /  |    /  ^  \\    |   \\|  |    /  ^  \\    |  \\  /  | |  |  __  |  \\  /  | |  |__   |   \\|  | `---|  |----`\n" +
                "|  |     |  | |   _  <  |      /      /  /_\\  \\   |      /      \\_    _/      |  |\\/|  |   /  /_\\  \\   |  . `  |   /  /_\\  \\   |  |\\/|  | |  | |_ | |  |\\/|  | |   __|  |  . `  |     |  |     \n" +
                "|  `----.|  | |  |_)  | |  |\\  \\----./  _____  \\  |  |\\  \\----.   |  |        |  |  |  |  /  _____  \\  |  |\\   |  /  _____  \\  |  |  |  | |  |__| | |  |  |  | |  |____ |  |\\   |     |  |     \n" +
                "|_______||__| |______/  | _| `._____/__/     \\__\\ | _| `._____|   |__|        |__|  |__| /__/     \\__\\ |__| \\__| /__/     \\__\\ |__|  |__|  \\______| |__|  |__| |_______||__| \\__|     |__|     \n" +
                "                                                                                                                                                                                               \n";
        System.out.println(text);
    }
    public static void printMenue(){
        PrintingService.printLibraryManagement();
        System.out.println("1. Add a New Book");
        System.out.println("2. Display Available Books");
        System.out.println("3. Search for a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. List Borrowed Books");
        System.out.println("6. Generate Report");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    public static void printBookTable(List<Book> books) {
        drawLine(7);
        System.out.printf("| %20s | %20s | %20s | %20s | %20s | %20s | %20s |\n",
                "isbn", "title", "Description", "Author", "Quantity", "Created by" ,"Created at");
        drawLine(7);
        books.forEach(book -> {
            String title = book.getTitre();
            String author = book.getAuthor();
            String description = book.getDescription();
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %20d | %20s | %20s |\n",
                    book.getIsbn(),
                    title.length() > 20 ? title.substring(1, 17) + "..." : title ,
                    description.length() > 20 ? book.getDescription().substring(1, 17) + "..." : description,
                    author.length() > 20 ? author.substring(1, 17) + "..." : author,
                    book.getQuantity(),
                    book.getCreated_by().getFirstname() + " " + book.getCreated_by().getLastname(),
                    book.getCreated_at());

            drawLine(7);
        });

    }
    public static void drawLine(int numberOfColumn){
        System.out.print('+');
        for (int i = 0; i < numberOfColumn; i ++) {
            for (int j = 0; j < 22; j ++) {
                System.out.print("-");
            }
            System.out.print('+');
        }

        System.out.print("\n");
    }

    public static void printHeader(String text){
        System.out.println("+----------------------------------------------+");
        System.out.printf("|--------------- %20s----------|", text);
        System.out.println("+----------------------------------------------+");
    }

}
