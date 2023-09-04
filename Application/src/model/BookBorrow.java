
package model;

import java.util.Date;

public class BookBorrow
{
    public int borrow_id;
    public String isbn;
    public int borrower_id;
    public String status;
    public Date borrow_date;
    public Date return_date;
    public Date created_at;
}