package service;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import model.Book;
import java.util.List;
import java.sql.Connection;

public class BookService
{
    private final Connection connection;

    public BookService() {
        this.connection = DataBase.getConnection();
    }

    public List<Book> getAllBooks() {
        final List<Book> books = new ArrayList<Book>();
        final String query = "SELECT * FROM book";
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                final String isbn = resultSet.getString("isbn");
                final String titre = resultSet.getString("titre");
                final String description = resultSet.getString("description");
                final String author = resultSet.getString("author");
                final int quantity = resultSet.getInt("quantity");
                final int created_by = resultSet.getInt("created_by");
                final Date created_at = resultSet.getDate("created_at");
                final Book book = new Book(isbn, titre, description, author, quantity, created_by, created_at);
                books.add(book);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
