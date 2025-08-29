package com.jiat.globemed.model;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reports {

    public void printFinancialReport(DefaultTableModel tableModel)  {
        try {
            HashMap<String, Object> reportmap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(Reports.class.getResourceAsStream("/reports/CustomerReport.jasper"), reportmap, new JRTableModelDataSource(tableModel));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
