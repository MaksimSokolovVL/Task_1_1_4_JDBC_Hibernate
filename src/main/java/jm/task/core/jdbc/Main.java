package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.ConnectionData;
import jm.task.core.jdbc.dao.SqlData;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class Main {

    public static void main(String[] args) {

//        UserServiceImpl userService = new UserServiceImpl();
//        userService.createUsersTable();
//
//        for (int i = 0; i < 4; i++) {
//            userService.saveUser(String.format("Name%d", i + 1),
//                    String.format("Last name%d", i + 2), (byte) (3 + (i * 2)));
//        }
//
//        userService.getAllUsers().forEach(System.out::println);
//        userService.cleanUsersTable();
//        userService.dropUsersTable();


        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", ConnectionData.DB_URL);
        configuration.setProperty("hibernate.connection.username", ConnectionData.DB_USER);
        configuration.setProperty("hibernate.connection.password", ConnectionData.DB_PASSWORD);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

//        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        //        = session.beginTransaction();


        Transaction transaction = null;
        try (
                SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                Session session = sessionFactory.openSession()
        ) {
            transaction = session.beginTransaction();

            session.createSQLQuery(SqlData.CREATE_USER_TAB).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<User> query = builder.createQuery(User.class);

//        query.from(User.class);
//        session.createQuery(query).executeUpdate();

//        transaction.commit();

//        session.close();
//        sessionFactory.close();
    }
}
