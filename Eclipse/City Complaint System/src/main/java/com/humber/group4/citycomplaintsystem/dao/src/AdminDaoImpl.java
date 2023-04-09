package com.humber.group4.citycomplaintsystem.dao.src;

import com.humber.group4.citycomplaintsystem.models.src.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AdminDaoImpl implements AdminDao {
    private final SessionFactory sessionFactory;

    public AdminDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(admin);
    }

    @Override
    @Transactional
    public Admin readById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Admin.class, id);
    }
}
