package service;

import java.sql.*;
import java.util.ArrayList;

import database.DataBase;
import interfaces.CRUD;
import model.Book;
import model.Librarian;

import java.util.List;

public class BookService implements CRUD <Book>
{
    private final Connection connection;

    public BookService() {
        this.connection = DataBase.getConnection();
    }

    @Override
    public Book save(Book book) throws SQLException {
        String query = "INSERT INTO book (isbn, titre, description, author, quantity, created_by, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getTitre());
        preparedStatement.setString(3, book.getDescription());
        preparedStatement.setString(4, book.getAuthor());
        preparedStatement.setInt(5, book.getQuantity());
        preparedStatement.setInt(6, book.getCreated_by().getId());
        preparedStatement.setDate(7, new java.sql.Date(book.getCreated_at().getTime()));

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            return book;
        } else {
            throw new SQLException("Failed to insert the book");
        }
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String query = "UPDATE book SET titre = ?, description = ?, author = ?, quantity = ?, created_by = ?, created_at = ? WHERE isbn = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, book.getTitre());
        preparedStatement.setString(2, book.getDescription());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setInt(4, book.getQuantity());
        preparedStatement.setInt(5, book.getCreated_by().getId());
        preparedStatement.setDate(6, new java.sql.Date(book.getCreated_at().getTime()));
        preparedStatement.setString(7, book.getIsbn());

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    }

    @Override
    public List<Book> read() {
        final List<Book> books = new ArrayList<>();
        final String query = "SELECT * FROM book b INNER JOIN librarian l ON b.created_by = l.id";
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // creator
                Librarian created_by = new Librarian(
                        resultSet.getInt("l.id"),
                        resultSet.getString("l.firstname"),
                        resultSet.getString("l.lastname"),
                        resultSet.getString("l.email"),
                        "********"
                        );
                Book book = new Book(
                        resultSet.getString("b.isbn"),
                        resultSet.getString("b.titre"),
                        resultSet.getString("b.description"),
                        resultSet.getString("b.author"),
                        resultSet.getInt("b.quantity"),
                        created_by,
                        resultSet.getDate("b.created_at")
                );
                books.add(book);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode() +" : "+ e.getMessage());
        }
        return books;
    }

    @Override
    public boolean delete(Book book) throws SQLException{
        String query = "DELETE FROM book where isbn = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getIsbn());
        int rowAffected = preparedStatement.executeUpdate();
        return rowAffected > 0;
    }
}
