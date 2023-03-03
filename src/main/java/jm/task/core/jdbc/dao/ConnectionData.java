package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData {

    public static final String DB_URL = "jdbc:mysql://80.78.246.73:3306/public";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "x0HuDgA5L2df";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD);
    }
}
