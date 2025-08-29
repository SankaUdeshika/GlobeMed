/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jiat.globemed.service;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Billing;

/**
 *
 * @author sanka
 */
public class ReportGenerate {

    public void generateAppointment(Appointment appointment) {
        Visitors reportGeneration = new ReportGenerating();
        AppointmentSummary appointmentSummary = new AppointmentSummary(appointment);
        appointmentSummary.accept(reportGeneration);
    }

    public void generateBilling(Billing billing) {
        Visitors reportGeneration = new ReportGenerating();
        FinancialSummary financialSummary = new FinancialSummary(billing);
        financialSummary.accept(reportGeneration);
    }
}

// Visitor Interface
interface Visitors {
    void visit(AppointmentSummary appointmentSummary);
    void visit(FinancialSummary financialSummary);
}

// Client Element Interface
interface ClientElement {
    void accept(Visitors visitor);
}

// Concrete Visitor
class ReportGenerating implements Visitors {

    @Override
    public void visit(AppointmentSummary appointmentSummary) {
        System.out.println("Appointment Report: ID = " 
                + appointmentSummary.getAppointment().getId());
    }

    @Override
    public void visit(FinancialSummary financialSummary) {
        System.out.println("Billing Report: ID = " 
                + financialSummary.getBilling().getId());
    }
}

// Concrete Element - Appointment
class AppointmentSummary implements ClientElement {

    private final Appointment appointment;

    public AppointmentSummary(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    @Override
    public void accept(Visitors visitor) {
        visitor.visit(this);
    }
}

// Concrete Element - Billing
class FinancialSummary implements ClientElement {

    private final Billing billing;

    public FinancialSummary(Billing billing) {
        this.billing = billing;
    }

    public Billing getBilling() {
        return billing;
    }

    @Override
    public void accept(Visitors visitor) {
        visitor.visit(this);
    }
}
