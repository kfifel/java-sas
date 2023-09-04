package model;

import database.DataBase;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Date;

public class Book
{
    public String isbn;
    public String titre;
    public String description;
    public String author;
    public int quantity;
    public int created_by;
    public Date created_at;

    public Book() {
    }

    public Book(final String isbn, final String titre, final String description, final String author, final int quantity, final int created_by, final Date created_at) {
        this.isbn = isbn;
        this.titre = titre;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Librarian getCreator() throws SQLException {
        final String query = "select * from librarian where id = ? ";
        final Connection connection = DataBase.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.created_by);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Librarian(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("email"), resultSet.getString("password"));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Book{isbn='" + this.isbn + '\'' + ", titre='" + this.titre + '\'' + ", description='" + this.description + '\'' + ", author='" + this.author + '\'' + ", quantity=" + this.quantity + ", created_by=" + this.created_by + ", created_at=" + this.created_at + '}';
    }
}