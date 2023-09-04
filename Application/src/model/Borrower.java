package model;


import java.util.Date;

public class Borrower {
    public int id;
    public String full_name;
    public Date created_at;

    public Borrower(int id, String full_name, Date created_at) {
        this.id = id;
        this.full_name = full_name;
        this.created_at = created_at;
    }

    public Borrower() {
    }

    public String toString() {
        return "Borrower{id=" + this.id + ", full_name='" + this.full_name + '\'' + ", created_at=" + this.created_at + '}';
    }
}
