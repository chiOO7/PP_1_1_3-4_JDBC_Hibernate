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
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS USER (ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,");
            sqlBuilder.append("NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, AGE INT NOT NULL);");
            statement.executeUpdate(sqlBuilder.toString());
            connection.commit();
        } catch (Exception e) {
            try {
                e.printStackTrace();
                if (!(connection == null)) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (Exception e) {
            try {
                if (!(connection == null)) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO user (NAME, LAST_NAME, AGE) VALUES (\"" +
                    name + "\", \"" + lastName + "\", " + age + ");";
            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            try {
                if (!(connection == null)) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        pollDB("DELETE FROM user WHERE ID = " + id + ";");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT ID, NAME, LAST_NAME, AGE FROM user;";
            ResultSet rs = statement.executeQuery(sql);
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
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        pollDB("TRUNCATE TABLE user;");
    }

    private void pollDB(String sql) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            try {
                if (!(connection == null)) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
