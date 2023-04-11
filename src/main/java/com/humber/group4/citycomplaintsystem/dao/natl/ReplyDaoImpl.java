package com.humber.group4.citycomplaintsystem.dao.natl;

import com.humber.group4.citycomplaintsystem.models.mvd.Complaint;
import com.humber.group4.citycomplaintsystem.models.natl.Reply;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReplyDaoImpl implements ReplyDao {
    SessionFactory sessionFactory;

    public ReplyDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    @Transactional
    public void create(Reply reply) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(reply);
    }

    @Override
    @Transactional
    public List<Complaint> listByComplaint(Complaint read) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Reply where complaint = :complaint", Complaint.class)
                .setParameter("complaint", read)
                .getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Reply reply = session.get(Reply.class, id);
        session.remove(reply);
    }

    @Override
    @Transactional
    public Reply readById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Reply.class, id);
    }
}
