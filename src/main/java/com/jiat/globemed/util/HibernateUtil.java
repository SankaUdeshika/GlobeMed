package com.jiat.globemed.util;
import com.jiat.globemed.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml"); // reads from resources
            cfg.addAnnotatedClass(Staff.class);
            cfg.addAnnotatedClass(Patient.class);// must include this
            cfg.addAnnotatedClass(TreatmentPlan.class);
            cfg.addAnnotatedClass(Appointment.class);
//            cfg.addAnnotatedClass(InsuranceClaim.class);
//            cfg.addAnnotatedClass(MedicalReport.class);
//            cfg.addAnnotatedClass(Medication.class);
//            cfg.addAnnotatedClass(Prescription.class);

            // optionally register annotated classes:
            // cfg.addAnnotatedClass(com.yourorg.globemed.model.Patient.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            return cfg.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
