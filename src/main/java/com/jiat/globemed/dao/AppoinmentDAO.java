package com.jiat.globemed.dao;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AppoinmentDAO {
    public void saveAppoinment(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(appointment);
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

    public List<Appointment> getAllAppointments() {
        Transaction tx = null;
        List<Appointment> appointments = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // HQL query to fetch all appointments
            appointments = session.createQuery("from Appointment", Appointment.class).list();

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
        return appointments;
    }


    public List<Appointment> getCompletedAllAppointments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Eagerly fetch both Appointment + Patient in the same query
            return session.createQuery(
                            "select a from Appointment a " +
                                    "join fetch a.patient " +   // fetch patient eagerly
                                    "where a.status = :status",
                            Appointment.class
                    )
                    .setParameter("status", Appointment.Status.COMPLETED)
                    .list();
        }
    }


    public Appointment findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Appointment appointment = session.get(Appointment.class, id);
            if(appointment != null) {
                return appointment;
            }else{
                return null;
            }
        }
    }

    public void updateAppoinment(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(appointment);
            tx.commit();
        }
    }
    public void cancelAppoinment(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            appointment.setStatus(Appointment.Status.CANCELLED);
            session.merge(appointment);
            tx.commit();
        }
    }
}
