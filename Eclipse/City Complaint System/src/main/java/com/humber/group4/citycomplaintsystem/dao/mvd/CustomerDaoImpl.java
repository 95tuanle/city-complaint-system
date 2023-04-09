package com.humber.group4.citycomplaintsystem.dao.mvd;


import com.humber.group4.citycomplaintsystem.models.mvd.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class CustomerDaoImpl implements CustomerDao {

    final SessionFactory sessionFactory;

    public CustomerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Customer readById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Customer.class, id);
    }

    @Override
    @Transactional
    public void create(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(customer);
    }
}
