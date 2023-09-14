package repository;

import database.DataBase;
import interfaces.CRUD;
import entities.Book;
import entities.Librarian;
import security.Authentication;
import service.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements CRUD<Book> {
    private final Connection connection;
    public BookRepository() {
        connection = DataBase.getConnection();
    }

    @Override
    public Book save(Book book) throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(book);
        preparedStatement.setInt(5, book.getQuantity());
        preparedStatement.setInt(6, book.getCreated_by().getId());
        preparedStatement.setDate(7, new java.sql.Date(book.getCreated_at().getTime()));

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            return book;
        } else {
            Logger.error("Failed to insert the book");
            throw new SQLException("Failed to insert the book");
        }
    }

    private PreparedStatement getPreparedStatement(Book book) throws SQLException {
        String query = "INSERT INTO book " +
                "(isbn, titre, description, author, quantity, created_by, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setString(2, book.getTitre());
        preparedStatement.setString(3, book.getDescription());
        preparedStatement.setString(4, book.getAuthor());
        return preparedStatement;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String query = "UPDATE book SET " +
                "titre = ?, description = ?, author = ?, quantity = ?, created_by = ?, created_at = ?" +
                " WHERE isbn = ?";

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
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book b INNER JOIN librarian l ON b.created_by = l.id WHERE quantity > 0";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // creator
                Librarian created_by = new Librarian(
                        resultSet.getInt("l.id"),
                        resultSet.getString("l.firstname"),
                        resultSet.getString("l.lastname"),
                        resultSet.getString("l.email")
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

    public Book findByIsbn(String isbn) throws SQLException {
        Librarian librarian;
        Book book = null;
        String query = "SELECT * FROM " +
                "book b INNER JOIN librarian l On b.created_by = l.id" +
                " WHERE isbn = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, isbn);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            if (resultSet.getInt("l.id") == Authentication.librarian.getId())
                librarian = Authentication.librarian;
            else
                librarian = new Librarian(
                        resultSet.getInt("l.id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email")
                );

            book = new Book(
                    resultSet.getString("isbn"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getString("author"),
                    resultSet.getInt("quantity"),
                    librarian,
                    resultSet.getDate("created_at")
            );
        }
        return book;
    }

    public int countBooks() throws SQLException {
        String query = "SELECT COUNT(*) FROM book";

        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    public Book getMostBorrowedBook() throws SQLException{
        String query = "SELECT bk.isbn, COUNT(br.isbn) AS count " +
                "FROM book bk " +
                "INNER JOIN book_borrow br ON bk.isbn = br.isbn " +
                "GROUP BY bk.isbn " +
                "ORDER BY count DESC " +
                "LIMIT 1";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            String isbn = resultSet.getString("isbn");
            int count = resultSet.getInt("count");
            Book book = findByIsbn(isbn);

            if (book != null) {
                book.setBorrowCount(count);
                return book;
            }
        }
        return null;
    }
}
