package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;


import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS USER (ID BIGINT, " +
            "NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, AGE TINYINT NOT NULL);";
    private static final String SQL_DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS USER;";
    private static final String HQL_GET_ALL_USERS_STATEMENT = "FROM User";
    private static final String HQL_CLEAR_TABLE_STATEMENT = "DELETE FROM User";


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_CREATE_TABLE_STATEMENT, User.class).executeUpdate();
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
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL_DROP_TABLE_STATEMENT, User.class).executeUpdate();
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
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
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
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
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
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery(HQL_GET_ALL_USERS_STATEMENT, User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(HQL_CLEAR_TABLE_STATEMENT, null).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                e.printStackTrace();
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
