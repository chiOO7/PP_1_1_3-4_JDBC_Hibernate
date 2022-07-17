package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection;
    private static Session session;
    private static SessionFactory sessionFactory;

    static {
//        connection = getMySQLConnection();
        session = getHibernateSessionFactory("localhost", "pp_1_1_3_shema", "root", "1987");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Session getSession() {
        return session;
    }

    private static Connection getMySQLConnection() {
        String hostName = "localhost";
        String dbName = "pp_1_1_3_shema";
        String userName = "root";
        String password = "1987";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    private static Connection getMySQLConnection(String hostName, String dbName,
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

    private static Session getHibernateSessionFactory(String hostName, String dbName,
                                                    String userName, String password) {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, connectionURL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory.openSession();
    }
}
