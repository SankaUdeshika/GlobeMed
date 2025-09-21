package com.jiat.globemed.dao;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.model.TreatmentPlan;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientDAO {

    public void savePatient(Patient patient) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(patient);
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

    public void savePatientTreatment(TreatmentPlan treatmentPlan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(treatmentPlan);
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

    public void updatePatient(Patient patient) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(patient);
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

    public Patient findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Patient patient = session.get(Patient.class, id);
            if (patient != null) {
                return patient;
            } else {
                return null;
            }
        }
    }

    public List<Patient> getAllPatients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Eagerly fetch both Appointment + Patient in the same query
            return session.createQuery( "select a from Patient a ",Patient.class).list();
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
