package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book
{
    private String isbn;
    private String titre;
    private String description;
    private String author;
    private int quantity;
    private Librarian created_by;
    private Date created_at;
    private List<BookBorrow> bookBorrowList;

    public Book() {}

    public Book(String isbn, String titre, String description, String author, int quantity, Librarian created_by, Date created_at) {
        this.isbn = isbn;
        this.titre = titre;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.created_by = created_by;
        this.created_at = created_at;

        bookBorrowList = new ArrayList<>();
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

    public Librarian getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Librarian created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public List<BookBorrow> getBookBorrowList() {
        return bookBorrowList;
    }

    public void addBookBorrow(BookBorrow bookBorrowList) {
        this.bookBorrowList.add(bookBorrowList);
    }

    @Override
    public String toString() {
        return "Book{isbn='" + this.isbn + '\'' + ", titre='" + this.titre + '\'' + ", description='" + this.description + '\'' + ", author='" + this.author + '\'' + ", quantity=" + this.quantity + ", created_by=" + this.created_by + ", created_at=" + this.created_at + '}';
    }
    private int borrowCount;

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }
}