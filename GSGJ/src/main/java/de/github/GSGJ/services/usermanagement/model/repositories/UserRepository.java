package de.github.GSGJ.services.usermanagement.model.repositories;

import de.github.GSGJ.services.usermanagement.model.entities.User;
import de.github.GSGJ.services.usermanagement.model.entities.Usergroup;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by claudio on 19.06.17.
 */
public class UserRepository {
    private static Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static SessionFactory sessionFactory;

    public UserRepository(SessionFactory factory) {
        sessionFactory = factory;
    }

    public int addUser(String name, String email, ArrayList<Usergroup> groups) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
            User user = new User(name, email, groups);
            id = (int) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return id;
    }

//    public int updateUser(User user) {
//
//    }
//
//    public int deleteUser(User user) {
//
//    }
//
//    public User findByPk(int id) {
//
//    }


}
