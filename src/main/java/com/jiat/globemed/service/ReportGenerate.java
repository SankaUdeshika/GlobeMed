/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jiat.globemed.service;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Billing;
import com.jiat.globemed.model.Reports;
import com.jiat.globemed.model.TreatmentPlan;
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

    // Generate Medical Treatment Report
    public void generateMedicalTreatment(DefaultTableModel tableModel) {
        Visitors reportGeneration = new ReportGenerating();
        TreatmentSummary treatmentSummary = new TreatmentSummary(tableModel);
        treatmentSummary.accept(reportGeneration);
    }

    // Generate Billing Report
    public void generateBilling(DefaultTableModel tableModel) {
        Visitors reportGeneration = new ReportGenerating();
        FinancialSummary financialSummary = new FinancialSummary(tableModel);
        financialSummary.accept(reportGeneration);
    }
}

// Visitor Interface
interface Visitors {

    void visit(TreatmentSummary treatmentSummary);

    void visit(FinancialSummary financialSummary);
}

// Client Element Interface
interface ClientElement {

    void accept(Visitors visitor);
}

// Concrete Visitor
class ReportGenerating implements Visitors {

    @Override
    public void visit(TreatmentSummary treatmentSummary) {
        try {
            HashMap<String, Object> reportmap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    Reports.class.getResourceAsStream("/reports/PatientTreatments.jasper"),
                    reportmap,
                    new JRTableModelDataSource(treatmentSummary.getTreatmentPlan())
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(FinancialSummary financialSummary) {
        try {
            HashMap<String, Object> reportmap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    Reports.class.getResourceAsStream("/reports/GlobmedBilling.jasper"),
                    reportmap,
                    new JRTableModelDataSource(financialSummary.getBilling())
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Concrete Element - Treatment
class TreatmentSummary implements ClientElement {

    private final DefaultTableModel treatmentTableModel;

    public TreatmentSummary(DefaultTableModel treatmentTableModel) {
        this.treatmentTableModel = treatmentTableModel;
    }

    public DefaultTableModel getTreatmentPlan() {
        return treatmentTableModel;
    }

    @Override
    public void accept(Visitors visitor) {
        visitor.visit(this);
    }
}

// Concrete Element - Billing
class FinancialSummary implements ClientElement {

    private final DefaultTableModel tableModel;

    public FinancialSummary(DefaultTableModel tableModel) {
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
