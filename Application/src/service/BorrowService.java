package service;

import model.Book;
import model.BookBorrow;
import model.Borrower;
import repository.BorrowRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BorrowService {
    private BorrowRepository borrowRepository;

    public BorrowService() {
        borrowRepository = new BorrowRepository();
    }

    public BookBorrow borrowBook(BookBorrow bookBorrow) {
        Logger.info("Borrowing a book with title:"+ bookBorrow.getBook().getTitre()+" from the Borrower: "+bookBorrow.getBorrower().getFull_name());
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
}
