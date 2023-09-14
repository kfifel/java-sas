package entities.dto;

import entities.Book;

public class StaticticsData {
    public int bookCount;
    public int borrowerCount;
    public Book mostBorrow;
    public int borrowCountThisYear;
    public StaticticsData () {}
    public StaticticsData(int bookCount, int borrowerCount, Book mostBorrow, int borrowCountThisYear) {
        this.bookCount = bookCount;
        this.borrowerCount = borrowerCount;
        this.mostBorrow = mostBorrow;
        this.borrowCountThisYear = borrowCountThisYear;
    }
}
