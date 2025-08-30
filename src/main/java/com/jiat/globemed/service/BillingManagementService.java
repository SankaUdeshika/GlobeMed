package com.jiat.globemed.service;

import com.jiat.globemed.dao.BillingDAO;
import com.jiat.globemed.model.Billing;
import com.jiat.globemed.model.InsuranceClaim;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.util.HibernateUtil;

import java.time.LocalDate;
import java.util.Date;
import org.hibernate.Session;

public class BillingManagementService {

    private Claim adminHandler;
    private Claim doctorHandler;

    public BillingManagementService() {
        adminHandler = new AdminHandler();
        doctorHandler = new DoctorHandler();
        adminHandler.setNextHandler(doctorHandler);
    }

    //Direct Billing
    public Billing createBilling(int patientId, double amount, LocalDate date, Billing.Status status) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            Billing billing = new Billing(patient, amount, date, status.PAID, null);
            return billing;
        }
    }

    public void payBilling(Billing billing) {
        billing.setStatus(Billing.Status.PAID);
        System.out.println("Billing paid for patient: " + billing.getPatient().getName());
        new BillingDAO().saveBilling(billing);
    }

    //Direct Billing
    public void adminSubmit(Billing billing) {
        adminHandler.handleClaim(billing);
    }

    public void doctorApprove(Billing billing) {
        doctorHandler.handleClaim(billing);
    }

}

abstract class Claim {

    protected Claim nextHandler;

    public void setNextHandler(Claim handler) {
        this.nextHandler = handler;
    }

    public abstract void handleClaim(Billing bill);
}

class AdminHandler extends Claim {

    @Override
    public void handleClaim(Billing bill) {
        if (bill.getInsuranceClaim() != null
                && bill.getInsuranceClaim().getClaimStatus() == InsuranceClaim.ClaimStatus.SUBMITTED) {

            bill.getInsuranceClaim().setClaimStatus(InsuranceClaim.ClaimStatus.PROCESSING);

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.merge(bill);
                session.getTransaction().commit();
            }

        }

    }
}

class DoctorHandler extends Claim {

    @Override
    public void handleClaim(Billing bill) {
        if (bill.getInsuranceClaim().getClaimStatus() == InsuranceClaim.ClaimStatus.PROCESSING) {
            bill.getInsuranceClaim().setClaimStatus(InsuranceClaim.ClaimStatus.APPROVED);

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                session.merge(bill);
                session.getTransaction().commit();
            }
        } else {
        }
    }
}
