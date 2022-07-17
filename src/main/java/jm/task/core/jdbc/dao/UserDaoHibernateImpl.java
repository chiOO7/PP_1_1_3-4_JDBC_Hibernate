package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
//import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSession;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            Session session = getSession();
            transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS USER (ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, AGE INT NOT NULL)";
//            String sql = "select table user (id int)";


                    Query query = session.createSqlQuery(sql).User.class();
                    query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            Session session = getSession();
            transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS user";

            Query query = session.createQuery(sql, User.class);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            Session session = getSession();
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object

            session.persist(new User(name, lastName, age));
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            Session session = Util.getSession();
            transaction = session.beginTransaction();
// Hibernate generates: "select from users where id = 1"
            User user = session.get(User.class, 1);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSession();
        return session.createQuery("from User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            Session session = Util.getSession();
            transaction = session.beginTransaction();
            session.clear();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
