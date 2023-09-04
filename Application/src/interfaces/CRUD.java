package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface CRUD <T> {
    T save(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    List<T> read() throws SQLException;
    boolean delete(T t) throws SQLException;
}
