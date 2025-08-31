package com.jiat.globemed.dao;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.model.TreatmentPlan;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TreatmentDAO {

    public List<TreatmentPlan> getPatientTreatmentsByPatientId(Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM TreatmentPlan t WHERE t.patient.id = :patientId", TreatmentPlan.class)
                    .setParameter("patientId", patientId)
                    .list();
        }
    }

    public void saveTreatment(TreatmentPlan treatmentPlan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(treatmentPlan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteTreatment(TreatmentPlan treatmentPlan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(treatmentPlan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    public TreatmentPlan findById(String treatmentPlanId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TreatmentPlan treatmentPlan = session.get(TreatmentPlan.class, treatmentPlanId);
            if(treatmentPlan != null) {
                return treatmentPlan;
            }else{
                return null;
            }
        }
    }

}
