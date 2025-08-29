package com.jiat.globemed.dao;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Billing;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BillingDAO {
    public void saveBilling(Billing billing) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(billing);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        }
    }

    public Billing getBilling(long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Billing billing = (Billing) session.get(Billing.class, id);
            tx.commit();
            return billing;
        }
    }

    public List<Billing> getAllBilling() {
        Transaction tx = null;
        List<Billing> billings = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            billings = session.createQuery(
                    "SELECT b FROM Billing b " +
                            "JOIN FETCH b.patient " +   // fetch patient eagerly
                            "LEFT JOIN FETCH b.insuranceClaim", // fetch insurance if exists
                    Billing.class
            ).list();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        }
        return billings;
    }




}
