package service;

import entities.Book;
import entities.BookBorrow;
import entities.Borrower;
import repository.BorrowRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BorrowService {
    private final BorrowRepository borrowRepository;

    public BorrowService() {
        borrowRepository = new BorrowRepository();
    }

    public BookBorrow borrowBook(BookBorrow bookBorrow) throws SQLException {
        Logger.info("Borrowing a book with title:"+ bookBorrow.getBook().getTitre()+" from the Borrower: "+bookBorrow.getBorrower().getFull_name());
        if (borrowRepository.hasBorrowedUnreturnedBook(bookBorrow.getBorrower().getId(), bookBorrow.getBook().getIsbn())) {
            ConsoleMessageService.error("Borrow has a book unreturned");
            return null;
        }
        else if (borrowRepository.hasBorrowedLostBook(bookBorrow.getBorrower().getId(), bookBorrow.getBook().getIsbn())) {
            ConsoleMessageService.error("Borrow has lost book");
            return null;
        }

        return borrowRepository.save(bookBorrow);
    }

    public List<BookBorrow> read() {
        try {
            return borrowRepository.read();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public BookBorrow findBookBorrowByBorrowerIdAndIsbn(Borrower borrower, Book isbn) {
        try {
            return borrowRepository.findBookBorrowByBorrowerIdAndIsbn(borrower, isbn);
        } catch (SQLException e) {
            Logger.error("Error ", e);
        }
        return null;
    }

    public int countBorrowsThisYear() {
        try {
            return borrowRepository.countBorrowsThisYear();
        }  catch (SQLException e) {
            Logger.error("Class BorrowRepository methode: countBorrowsThisYear", e );
            return -1;
        }
    }

    public boolean returned(BookBorrow bookBorrow) {
        boolean res = false;
        if (bookBorrow.getBook().getIsbn() == null || bookBorrow.getBorrower().getId() == 0 )
        {
            Logger.error("Some data is required!! while updating ");
            return false;
        }
        try{
            if (borrowRepository.returnBook(bookBorrow))
                res = true;
        }
        catch (SQLException e) {
            Logger.error("Error in changing the return of the book_borrow ", e);
        }
        return res;
    }
}
