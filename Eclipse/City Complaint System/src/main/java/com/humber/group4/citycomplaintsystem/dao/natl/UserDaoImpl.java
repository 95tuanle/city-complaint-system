package com.humber.group4.citycomplaintsystem.dao.natl;

import com.humber.group4.citycomplaintsystem.models.natl.User;
import com.humber.group4.citycomplaintsystem.models.natl.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    @Transactional
    public User readByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    @Transactional
    public List<User> listByType(String type) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User where type = :type", User.class)
                .setParameter("type", UserType.valueOf(type))
                .list();
    }
}
