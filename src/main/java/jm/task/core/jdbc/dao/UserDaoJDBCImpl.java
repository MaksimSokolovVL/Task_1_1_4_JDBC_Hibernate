package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Statement statement;
    private final Connection connection;

    public UserDaoJDBCImpl() {

        try {
            String SERVER_MYSQL_URL = "jdbc:mysql://80.78.246.73:3306/public";
            String USER = "root";
            String PASSWORD = "x0HuDgA5L2df";
            connection = DriverManager.getConnection(SERVER_MYSQL_URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void createUsersTable() {
        try {
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS public.users (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    last_name VARCHAR(45) NOT NULL,
                    second_name VARCHAR(45) NOT NULL,
                    age TINYINT NOT NULL,
                    PRIMARY KEY (`id`))
                    ENGINE = InnoDB;""";
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            String dropTableSQL = "DROP TABLE IF EXISTS users;";
            statement.executeUpdate(dropTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String SAVE_USER = """
                    INSERT INTO public.users (last_name, second_name, age)
                    VALUES ('%s', '%s', %d);""";
            statement.executeUpdate(String.format(SAVE_USER, name, lastName, age));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try {
            String DELETE_USER = """
                    DELETE
                    FROM public.users
                    WHERE id = %d;""";
            statement.executeUpdate(String.format(DELETE_USER, id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
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
        try {
            String CLEAN_TABLE = "TRUNCATE TABLE public.users;";
            statement.executeUpdate(CLEAN_TABLE);
            System.out.println("Чисто");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
