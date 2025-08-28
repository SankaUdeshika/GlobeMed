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

}
