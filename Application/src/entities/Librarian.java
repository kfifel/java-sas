package entities;

import java.util.ArrayList;
import java.util.List;

public class Librarian {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    // relation with table book that librarian create
    private List <Book> bookAdded;

    public Librarian() {
        bookAdded = new ArrayList<>();
    }

    public Librarian(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;

        bookAdded = new ArrayList<>();
    }
    public Librarian(int id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        bookAdded = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBookAdded() {
        return bookAdded;
    }

    public void addBookAdded(Book book) {
        bookAdded.add(book);
    }

    public String toString() {
        return "Librarian {id=" + this.id + ", firstname=" + this.firstname + ", lastname=" + this.lastname + ", email=" + this.email + "}";
    }
}