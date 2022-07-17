package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    static {
//        connection = getMySQLConnection();
        sessionFactory = getHibernateSessionFactory("localhost", "pp_1_1_3_shema", "root", "1987");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void shutDown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
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

    private static SessionFactory getHibernateSessionFactory(String hostName, String dbName,
                                                             String userName, String password) {

        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

                Properties settings = new Properties();
                String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, connectionURL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                serviceRegistryBuilder.applySettings(settings);
                serviceRegistry = serviceRegistryBuilder.build();

                MetadataSources metadata = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(User.class);

                sessionFactory = metadata.buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
            }
        }
        return sessionFactory;
    }
}
