package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;
    private static final String SQL_CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS USER (ID BIGINT, " +
            "NAME VARCHAR(50) NOT NULL, LAST_NAME VARCHAR(50) NOT NULL, AGE TINYINT NOT NULL);";
    private static final String SQL_DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS USER;";
    private static final String HQL_GET_ALL_USERS_STATEMENT = "FROM User";
    private static final String HQL_CLEAR_TABLE_STATEMENT = "DELETE FROM User";


    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        executeSQLTransaction(SQL_CREATE_TABLE_STATEMENT);
    }

    @Override
    public void dropUsersTable() {
        executeSQLTransaction(SQL_DROP_TABLE_STATEMENT);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(HQL_GET_ALL_USERS_STATEMENT, User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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

    private void executeSQLTransaction(String statement) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(statement, User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
