package model;

public class Librarian {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    public Librarian() {
    }

    public Librarian(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String toString() {
        return "Librarian {id=" + this.id + ", firstname=" + this.firstname + ", lastname=" + this.lastname + ", email=" + this.email + "}";
    }
}