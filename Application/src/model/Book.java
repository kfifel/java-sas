package model;

import database.DataBase;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Date;

public class Book
{
    private String isbn;
    private String titre;
    private String description;
    private String author;
    private int quantity;
    private int created_by;
    private Date created_at;

    public Book() {}

    public Book(String isbn, String titre, String description, String author, int quantity, int created_by, Date created_at) {
        this.isbn = isbn;
        this.titre = titre;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Librarian getCreator() throws SQLException {
        final String query = "select * from `librarian` where id = ? ";
        final Connection connection = DataBase.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.created_by);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Librarian(
                    resultSet.getInt("id"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                );
            }
        }
        return null;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Book{isbn='" + this.isbn + '\'' + ", titre='" + this.titre + '\'' + ", description='" + this.description + '\'' + ", author='" + this.author + '\'' + ", quantity=" + this.quantity + ", created_by=" + this.created_by + ", created_at=" + this.created_at + '}';
    }
}