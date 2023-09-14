package service;

import java.sql.*;

import repository.BookRepository;
import entities.Book;

import java.util.List;

public class BookService
{
    private final BookRepository bookRepository;

    public BookService() {
        bookRepository = new BookRepository();
    }

    public Book save(Book book) throws SQLException {
        Book bookSearched;
        if( (bookSearched = bookRepository.findByIsbn(book.getIsbn())) != null) {
            System.out.println("livre déjà exists!!\n voulez-vous augmenter la quantité 1: oui | 0: non");
            int input = PrintingService.getIntFromUser();
            if(input == 1) {
                bookSearched.setQuantity(book.getQuantity() + bookSearched.getQuantity());
                if (this.update(bookSearched)) {
                    Logger.info("augmenter la quantité du livre avec isbn: "+bookSearched.getIsbn()+" avec la quantity " + book.getQuantity() );
                    return bookSearched;
                }
            } else
                return null;
        }
        return bookRepository.save(book);
    }

    public boolean update(Book book) throws SQLException {
        return bookRepository.update(book);
    }

    public List<Book> read() {
        Logger.info("read book is called");
        return bookRepository.read();
    }

    public boolean delete(Book book) throws SQLException{
        return bookRepository.delete(book);
    }

    public Book findByIsbn(String isbn) {
        Book book = null;
        try {
            book = bookRepository.findByIsbn(isbn);
        }catch (SQLException e){
            Logger.error(e.toString());
        }
        return book;
    }

    public int countBooks() {
        try {
            return bookRepository.countBooks();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
        }

        return -1;
    }

    public Book getMostBorrowedBook() {
        try {
            return bookRepository.getMostBorrowedBook();
        } catch (SQLException e) {
            Logger.error("Error: ", e);
            return null;
        }
    }
}
