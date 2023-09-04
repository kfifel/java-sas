import service.BookService;

import java.sql.SQLException;

public class LibraryManagement {

    public static void main(String[] args)  {
        BookService bookService = new BookService();

        //bookService.getAllBooks().forEach(System.out::println);

        bookService.getAllBooks().forEach( book -> {
            try {
                System.out.println("books: =====================");
                System.out.println(book.getCreator());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
