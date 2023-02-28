package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {

        try {
            String SERVER_MYSQL_URL = "jdbc:mysql://80.78.246.73:3306/public";
            String USER = "root";
            String PASSWORD = "x0HuDgA5L2df";
            connection = DriverManager.getConnection(SERVER_MYSQL_URL, USER, PASSWORD);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void createUsersTable() {
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS public.users (
                id BIGINT NOT NULL AUTO_INCREMENT,
                last_name VARCHAR(45) NOT NULL,
                second_name VARCHAR(45) NOT NULL,
                age TINYINT NOT NULL,
                PRIMARY KEY (`id`))
                ENGINE = InnoDB;""");
    }

    public void dropUsersTable() {
        executeUpdate("DROP TABLE IF EXISTS users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        String SAVE_USER = """
                INSERT INTO public.users (last_name, second_name, age)
                VALUES ('%s', '%s', %d);""";
        executeUpdate(String.format(SAVE_USER, name, lastName, age));
    }

    public void removeUserById(long id) {
        String DELETE_USER = """
                DELETE
                FROM public.users
                WHERE id = %d;""";
        executeUpdate(String.format(DELETE_USER, id));
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String SELECT_FROM_PUBLIC_USERS = "SELECT * FROM public.users;";
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_PUBLIC_USERS);

            while (resultSet.next()) {
                userList.add(new User(
                                resultSet.getString("last_name"),
                                resultSet.getString("second_name"),
                                resultSet.getByte("age")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        executeUpdate("TRUNCATE TABLE public.users;");
    }

    private void executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
