package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        final UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        addFourRandomUsers(userService);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        try (Connection connection = Util.getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addFourRandomUsers(UserService userService) {
        userService.saveUser("Ivan", "Ivanov", (byte) 21);
        userService.saveUser("Andrey", "Andreev", (byte) 23);
        userService.saveUser("Egor", "Egorov", (byte) 19);
        userService.saveUser("Anna", "Ivanova", (byte) 22);
    }
}
