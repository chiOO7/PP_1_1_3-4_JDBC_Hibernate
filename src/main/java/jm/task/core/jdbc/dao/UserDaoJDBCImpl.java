package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try {
            StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE USER (ID BIGINT NOT NULL AUTO_INCREMENT,");
            sqlBuilder.append("NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, AGE INT NOT NULL,");
            sqlBuilder.append("PRIMARY KEY (ID))");
            try {
                connection.createStatement().executeUpdate(sqlBuilder.toString());
            } catch (SQLSyntaxErrorException e) {
                System.err.println("Table already exists");
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Connection to SQL ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try {
            String sql = "DROP TABLE user";

            try {
                connection.createStatement().executeUpdate(sql);
            } catch (SQLSyntaxErrorException e) {
                System.err.println("Table does not exists");
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Connection to SQL ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try {
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO user (NAME, LAST_NAME, AGE) VALUES (");
            sqlBuilder.append("\"").append(name).append("\", \"").append(lastName).append("\", ").append(age)
                    .append(") ");
            connection.createStatement().executeUpdate(sqlBuilder.toString());
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Connection to SQL ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        pollDB("DELETE FROM user WHERE ID = " + id);
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection connection = Util.getConnection();
        try {
            String sql = "SELECT ID, NAME, LAST_NAME, AGE FROM user";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                Long userId = rs.getLong(1);
                String userName = rs.getString("NAME");
                String userLastName = rs.getString("LAST_NAME");
                byte userAge = (byte) rs.getInt("AGE");
                User user = new User(userName, userLastName, userAge);
                user.setId(userId);
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Connection to SQL ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        pollDB("TRUNCATE TABLE user");
    }

    private void pollDB(String sql) {
        Connection connection = Util.getConnection();
        try {
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Connection to SQL ERROR");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
