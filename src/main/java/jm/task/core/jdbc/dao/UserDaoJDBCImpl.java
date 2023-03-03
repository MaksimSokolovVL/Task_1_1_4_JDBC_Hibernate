package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        executeUpdate(SqlData.CREATE_USER_TAB);
    }

    public void dropUsersTable() {
        executeUpdate(SqlData.DROP_USERS_TAB);
    }

    public void saveUser(String name, String lastName, byte age) {
        executeUpdate(String.format(SqlData.ADD_USER, name, lastName, age));
    }

    public void removeUserById(long id) {
        executeUpdate(String.format(SqlData.DELETE_USER, id));
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlData.SELECT_FROM_PUBLIC_USERS);

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
        executeUpdate(SqlData.CLEAN_USERS_TAB);
    }

    private void executeUpdate(String sql) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
