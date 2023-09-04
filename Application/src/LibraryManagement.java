import service.BookService;

import java.sql.SQLException;
public class LibraryManagement {
    private static final BookService bookService = new BookService();

    public static void main(String[] args)  {


        //bookService.getAllBooks().forEach(System.out::println);

        bookService.read().forEach( book -> {
            if(book.getCreated_by() == 1) {
                try {
                    bookService.delete(book);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                System.out.println("books: =====================");
                System.out.println(book.getCreator());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
