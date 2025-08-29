package com.jiat.globemed.service;

import com.jiat.globemed.model.Billing;
import com.jiat.globemed.model.InsuranceClaim;
import com.jiat.globemed.model.Patient;

import java.time.LocalDate;

public class BillingManagementService {

    public Billing createBilling(Patient patient, double amount, LocalDate date, Billing.Status status) {
        return new Billing(patient, amount, date, status);
    }
    public void addInsuranceClaim(Billing billing, String provider, String policyNumber) {
        InsuranceClaim claim = new InsuranceClaim(billing, provider, policyNumber, InsuranceClaim.ClaimStatus.SUBMITTED);
        billing.setInsuranceClaim(claim);

        AdminHandler adminHandler = new AdminHandler();
        DoctorHandler doctorHandler = new DoctorHandler();
        adminHandler.setNextHandler(doctorHandler);

    }
    public void payBilling(Billing billing) {
        billing.setStatus(Billing.Status.PAID);
    }
}

 abstract class ClaimHandler {
    protected ClaimHandler nextHandler;

    public void setNextHandler(ClaimHandler handler) {
        this.nextHandler = handler;
    }
    public abstract void handleClaim(InsuranceClaim claim);
}



 class AdminHandler extends ClaimHandler {
    @Override
    public void handleClaim(InsuranceClaim claim) {
        if (claim.getClaimStatus() == InsuranceClaim.ClaimStatus.SUBMITTED) {
            System.out.println("Admin approves basic validation");
            claim.setClaimStatus(InsuranceClaim.ClaimStatus.PROCESSING);
        }
        if (nextHandler != null) nextHandler.handleClaim(claim);
    }
}

 class DoctorHandler extends ClaimHandler {
    @Override
    public void handleClaim(InsuranceClaim claim) {
        if (claim.getClaimStatus() == InsuranceClaim.ClaimStatus.PROCESSING) {
            System.out.println("Insurance Officer reviews claim");
            // Approve or Reject randomly for demo
            claim.setClaimStatus(InsuranceClaim.ClaimStatus.APPROVED);
        }
        if (nextHandler != null) nextHandler.handleClaim(claim);
    }
}