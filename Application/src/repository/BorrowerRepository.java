package repository;

import database.DataBase;
import interfaces.CRUD;
import entities.Borrower;
import service.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowerRepository implements CRUD<Borrower> {

    public BorrowerRepository() {}

    @Override
    public Borrower save(Borrower borrower) throws SQLException {
        String query = "INSERT INTO borrower (full_name, created_at) VALUES (?, ?)";
        Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, borrower.getFull_name());
        preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));

        int rowsUpdates = preparedStatement.executeUpdate();

        if(rowsUpdates > 0){
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                borrower.setId(generatedId);
            }

            generatedKeys.close();
        }
        preparedStatement.close();
       return borrower;
    }

    @Override
    public boolean update(Borrower borrower) {
        String query = "UPDATE borrower SET full_name = ? WHERE id = ?";
        Connection connection = DataBase.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, borrower.getFull_name());
            preparedStatement.setLong(2, borrower.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
            return false;
        } finally {
            DataBase.disconnect();
        }
    }

    @Override
    public List<Borrower> read() {
        List<Borrower> borrowers = new ArrayList<>();

        String query = "SELECT * FROM borrower";
        Connection connection = DataBase.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                Date createdAt = resultSet.getDate("created_at");

                borrowers.add(new Borrower(id, fullName, createdAt));
            }
        } catch (SQLException e) {
            Logger.error(e.toString());
        } finally {
            DataBase.disconnect();
        }
        return borrowers;
    }

    @Override
    public boolean delete(Borrower borrower) {
        String query = "DELETE FROM borrower WHERE id = ?";
        Connection connection = DataBase.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, borrower.getId());

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            Logger.error(e.toString());
            return false;
        } finally {
            DataBase.disconnect();
        }
    }

    public Borrower findById(int borrowerId) {
        String query = "SELECT * FROM borrower WHERE id = ?";
        Connection connection = DataBase.getConnection();
        Borrower borrower = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, borrowerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                borrower = new Borrower();
                borrower.setId(resultSet.getInt("id"));
                borrower.setFull_name(resultSet.getString("full_name"));
                borrower.setCreated_at(resultSet.getDate("created_at"));
            }
        } catch (SQLException e) {
            Logger.error(e.getMessage(), e);
        }
        return borrower;
    }

    public int countBorrowers() throws SQLException{
        String query = "SELECT COUNT(*) FROM borrower";
        Connection connection = DataBase.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
}
