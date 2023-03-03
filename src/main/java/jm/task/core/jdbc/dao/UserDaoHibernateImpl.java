package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory;

    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.buildSessionFactory();
    }


    @Override
    public void createUsersTable() {
        executeTransaction(SqlData.CREATE_USER_TAB);
    }

    @Override
    public void dropUsersTable() {
        executeTransaction(SqlData.DROP_USERS_TAB);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
//        executeTransaction(String.format(SqlData.ADD_USER, name, lastName, age)); //todo создал 2 варианта, т.к. не знаю каким лучше для ДЗ

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
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
//        executeTransaction(String.format(SqlData.DELETE_USER, id)); //todo создал 2 варианта, т.к. не знаю каким лучше для ДЗ

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
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
        try (Session session = sessionFactory.openSession()
        ) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            return session.createQuery(criteria).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        executeTransaction(SqlData.CLEAN_USERS_TAB);
    }

    private void executeTransaction(String sql) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
