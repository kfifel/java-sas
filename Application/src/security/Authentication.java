package security;

import database.DataBase;
import entities.Librarian;
import service.ConsoleMessageService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    public static Librarian librarian;

    public static boolean login(String email, String password) throws SQLException {
        Connection connection = DataBase.getConnection();
        String query = "SELECT id, firstname, lastname ,email FROM librarian WHERE email = ? AND password = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String userEmail = resultSet.getString("email");

            Librarian librarian1 = new Librarian();
            librarian1.setEmail(userEmail);
            librarian1.setFirstname(firstname);
            librarian1.setLastname(lastname);
            librarian1.setId(id);

            Authentication.librarian = librarian1;

            resultSet.close();
            preparedStatement.close();
            ConsoleMessageService.success("You are logged in!!");
            return true;
        } else {
            resultSet.close();
            preparedStatement.close();
            ConsoleMessageService.error("Emil or password failed !!");

            return false;
        }

    }
    public static void logout() {
        Authentication.librarian = null;
        ConsoleMessageService.warning("you are logged out");
    }
}
