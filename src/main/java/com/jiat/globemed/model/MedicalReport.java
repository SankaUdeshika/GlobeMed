package com.jiat.globemed.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_reports")
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many reports belong to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReportType reportType;

    // Report created by a staff member (doctor, nurse, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Staff createdBy;

    // Could store plain text OR a file path (e.g., PDF storage path)
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate createdDate;

    // Enum for Report Types
    public enum ReportType {
        DIAGNOSTIC,
        TREATMENT_SUMMARY,
        FINANCIAL
    }

    // Constructors
    public MedicalReport() {}

    public MedicalReport(Patient patient, ReportType reportType, Staff createdBy, String content, LocalDate createdDate) {
        this.patient = patient;
        this.reportType = reportType;
        this.createdBy = createdBy;
        this.content = content;
        this.createdDate = createdDate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public ReportType getReportType() { return reportType; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }

    public Staff getCreatedBy() { return createdBy; }
    public void setCreatedBy(Staff createdBy) { this.createdBy = createdBy; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
