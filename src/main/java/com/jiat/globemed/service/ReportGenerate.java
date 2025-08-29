/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jiat.globemed.service;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Billing;
import com.jiat.globemed.model.Reports;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;

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

    public void generateBilling(DefaultTableModel tableModel) {
        Visitors reportGeneration = new ReportGenerating();
        FinancialSummary financialSummary = new FinancialSummary(tableModel);
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
        try {
            HashMap<String, Object> reportmap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(Reports.class.getResourceAsStream("/reports/GlobmedBilling.jasper"), reportmap, new JRTableModelDataSource(financialSummary.getBilling()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private final DefaultTableModel tableModel;

    public FinancialSummary( DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public DefaultTableModel getBilling() {
        return tableModel;
    }

    @Override
    public void accept(Visitors visitor) {
        visitor.visit(this);
    }
}
