package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        final UserDao userService = new UserDaoHibernateImpl();

        userService.createUsersTable();
//        addFourRandomUsers(userService);
//        userService.getAllUsers().forEach(System.out::println);
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
//        try (Connection connection = Util.getConnection()) {
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try (Session session = Util.getSession()){

        }
    }

    static void addFourRandomUsers(UserService userService) {
        userService.saveUser("Ivan", "Ivanov", (byte) 21);
        userService.saveUser("Andrey", "Andreev", (byte) 23);
        userService.saveUser("Egor", "Egorov", (byte) 19);
        userService.saveUser("Anna", "Ivanova", (byte) 22);
    }

    static void addFourRandomUsers(UserDao userService) {
        userService.saveUser("Ivan", "Ivanov", (byte) 21);
        userService.saveUser("Andrey", "Andreev", (byte) 23);
        userService.saveUser("Egor", "Egorov", (byte) 19);
        userService.saveUser("Anna", "Ivanova", (byte) 22);
    }
}
