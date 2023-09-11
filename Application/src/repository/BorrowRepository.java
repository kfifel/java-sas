package repository;

import database.DataBase;
import ennumiration.BookBorrowStatus;
import interfaces.CRUD;
import model.Book;
import model.BookBorrow;
import model.Borrower;
import service.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowRepository implements CRUD<BookBorrow> {
    private Connection connection;

    public BorrowRepository() {
        this.connection = DataBase.getConnection();
    }

    @Override
    public BookBorrow save(BookBorrow bookBorrow) {
        String query = "INSERT INTO book_borrow(isbn, borrower_id, status, borrow_date, return_date) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, bookBorrow.getBook().getIsbn());
            preparedStatement.   setInt(2, bookBorrow.getBorrower().getId());
            preparedStatement.setString(3, bookBorrow.getStatus().name());
            preparedStatement.  setDate(4, new java.sql.Date(new Date().getTime()));
            preparedStatement.  setDate(5, new java.sql.Date(bookBorrow.getReturn_date().getTime()));

            int rowUpdates = preparedStatement.executeUpdate();
            if(rowUpdates > 0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    bookBorrow.setId(generatedId);
                }
            }
            return bookBorrow;
        }catch (SQLException e){
            Logger.error("Error: ", e);
            return null;
        }
    }

    @Override
    public boolean update(BookBorrow bookBorrow) throws SQLException {
        return false;
    }

    @Override
    public List<BookBorrow> read() throws SQLException {
        List<BookBorrow> bookBorrowList = new ArrayList<>();

        String query = "SELECT * FROM book_borrow bk INNER JOIN book b ON bk.isbn = b.isbn" +
                " INNER JOIN borrower br ON br.id = bk.borrower_id"+
                " WHERE bk.status = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, BookBorrowStatus.TAKEN.name());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            BookBorrow bookBorrow = new BookBorrow();
            bookBorrow.setId(resultSet.getInt("id"));

            String statusStr = resultSet.getString("status");
            bookBorrow.setStatus(BookBorrowStatus.valueOf(statusStr));

            bookBorrow.setReturn_date(resultSet.getDate("return_date"));
            bookBorrow.setBorrow_date(resultSet.getDate("borrow_date"));

            Borrower borrower = new Borrower(
                    resultSet.getInt("br.id"),
                    resultSet.getString("full_name"),
                    resultSet.getDate("created_at")
                );

            Book book = new Book();
            book.setTitre(resultSet.getString("titre"));
            book.setIsbn(resultSet.getString("isbn"));
            book.setAuthor(resultSet.getString("author"));

            bookBorrow.setBorrower(borrower);
            bookBorrow.setBook(book);

            bookBorrowList.add(bookBorrow);
        }
        resultSet.close();
        return bookBorrowList;

    }

    @Override
    public boolean delete(BookBorrow bookBorrow) throws SQLException {
        return false;
    }

    public int countBorrowsThisYear() throws SQLException{
        String query = "SELECT COUNT(*) FROM book_borrow WHERE YEAR(borrow_date) = YEAR(NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1;
    }

    public BookBorrow findBookBorrowByBorrowerIdAndIsbn(Borrower borrower, Book book) throws SQLException {
        String query = "SELECT * FROM book_borrow where isbn = ? AND borrower_id = ?";
        BookBorrow bookBorrow = null;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, book.getIsbn());
        preparedStatement.setInt(2, borrower.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            bookBorrow = new BookBorrow();
            bookBorrow.setId(resultSet.getInt("id"));
            bookBorrow.setBorrower(borrower);
            bookBorrow.setBook(book);
            bookBorrow.setStatus(resultSet.getObject("status", BookBorrowStatus.class));
            bookBorrow.setReturn_date(resultSet.getDate("returned_date"));
            bookBorrow.setBorrow_date(resultSet.getDate("borrow_date"));
        }
        return bookBorrow;
    }
}
