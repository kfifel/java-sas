package model.dto;

import model.Book;

public class StaticticsData {
    public int BookCount;
    public int BorrowerCount;
    public Book MostBorrow;
    public int BorrowCountThisYear;
    public StaticticsData () {}
    public StaticticsData(int bookCount, int borrowerCount, Book mostBorrow, int borrowCountThisYear) {
        BookCount = bookCount;
        BorrowerCount = borrowerCount;
        MostBorrow = mostBorrow;
        BorrowCountThisYear = borrowCountThisYear;
    }
}
