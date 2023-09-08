package service;

import java.sql.*;

import repository.BookRepository;
import model.Book;

import java.util.List;

public class BookService
{
    private final BookRepository bookRepository;

    public BookService() {
        bookRepository = new BookRepository();
    }

    public Book save(Book book) throws SQLException {
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
}
