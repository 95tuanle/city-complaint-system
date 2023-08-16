package com.humber.group4.citycomplaintsystem.dao.mvd;

import com.humber.group4.citycomplaintsystem.models.mvd.Complaint;
import com.humber.group4.citycomplaintsystem.models.mvd.Customer;
import com.humber.group4.citycomplaintsystem.models.src.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ComplaintDaoImpl implements ComplaintDao {
    final SessionFactory sessionFactory;

    public ComplaintDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Complaint complaint) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(complaint);
    }

    @Override
    @Transactional
    public Complaint read(long id) {
        return sessionFactory.getCurrentSession().get(Complaint.class, id);
    }

    @Override
    @Transactional
    public void update(Complaint complaint) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(complaint);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Delete the complaint
        Session session = sessionFactory.getCurrentSession();
        Complaint complaint = session.get(Complaint.class, id);

        if (complaint != null) {
            session.remove(complaint);
        }
    }

    @Override
    @Transactional
    public List<Complaint> list() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Complaint ", Complaint.class).list();
    }

    @Override
    @Transactional
    public List<Complaint> listByCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Complaint where customer = :customer", Complaint.class)
                .setParameter("customer", customer)
                .list();
    }

    @Override
    @Transactional
    public List<Complaint> listByEmployee(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Complaint where employee = :employee", Complaint.class)
                .setParameter("employee", employee)
                .list();
    }
}
