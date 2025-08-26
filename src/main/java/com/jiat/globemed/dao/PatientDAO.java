package com.jiat.globemed.dao;

import com.jiat.globemed.model.Patient;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientDAO {
    public Patient save(Patient p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(p);
            tx.commit();
            return p;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Patient findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Patient.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Patient> searchByName(String q) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Patient p where lower(p.firstName) like :q or lower(p.lastName) like :q")
                    .setParameter("q", "%" + q.toLowerCase() + "%")
                    .list();
        }
    }
    // update, delete etc...
}
