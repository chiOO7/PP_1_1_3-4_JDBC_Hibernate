package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection;

    static {
        connection = getMySQLConnection();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Connection getMySQLConnection() {
        String hostName = "localhost";
        String dbName = "pp_1_1_3_shema";
        String userName = "root";
        String password = "1987";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
