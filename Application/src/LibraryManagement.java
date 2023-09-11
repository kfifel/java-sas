import configuration.Properties;
import controller.MenuController;
import model.Librarian;
import repository.BorrowRepository;
import security.Authentication;
import service.BookService;
import service.BorrowService;
import service.BorrowerService;
import service.ReportService;

import java.sql.SQLException;
public class LibraryManagement {
    public static void main(String[] args) throws SQLException {

        BookService bookService = new BookService();
        ReportService reportService = new ReportService();
        BorrowService borrowService = new BorrowService();
        BorrowerService borrowerService = new BorrowerService();

        MenuController menuController = new MenuController(
                bookService, reportService, borrowService, borrowerService);

        if(Properties.DEVELOPMENT)
            Authentication.librarian = new Librarian(1, "khalid", "fifel", "khalid@gmail.com");

        (new BorrowRepository().read()).forEach(bookBorrow -> System.out.println(bookBorrow.toString()));
        menuController.start();
    }

}
