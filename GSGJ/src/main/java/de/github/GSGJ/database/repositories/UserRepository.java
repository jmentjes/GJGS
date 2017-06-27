package de.github.GSGJ.database.repositories;

import de.github.GSGJ.database.entities.User;
import de.github.GSGJ.database.entities.Usergroup;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claudio on 19.06.17.
 */
public class UserRepository implements Repository {
    private static Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static SessionFactory sessionFactory;

    public UserRepository(SessionFactory factory) {
        sessionFactory = factory;
    }

    public int registerUser(String name, String email,String password, ArrayList<Usergroup> groups) {
       return this.registerUser(new User(name, email, password, groups));
    }

    public int registerUser(User user){
        if(user.getGroups() == null){
            //TODO change this
            user.setGroups(new ArrayList<>());
        }
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();
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
//        Session session = sessionFactory.openSession();
//        Transaction tx = null;
//        int id = 0;
//    }
//
//    public int deleteUser(User user) {
//
//    }




    public List<User> findByNameAndPw(String name, String password){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        Criterion nameCrit = Restrictions.eq("name", name);
        Criterion pwCrit = Restrictions.eq("password", password);

        criteria.add(nameCrit);
        criteria.add(pwCrit);

        return criteria.list();
    }

    public User findByID(int id) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        Criterion criterion = Restrictions.eq("id",id);
        criteria.add(criterion);
        List list = criteria.list();

        if(list.size() != 1){
            return null;
        }

        session.close();

        return (User) list.get(0);
    }

    public static void main(String... args){
        SessionFactory factory;
        try {
            factory = new Configuration().configure("/hibernate/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        UserRepository userRepository = new UserRepository(factory);
        System.out.println(userRepository.findByID(3).getName());
    }

}
