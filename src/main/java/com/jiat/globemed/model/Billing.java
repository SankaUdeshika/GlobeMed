package com.jiat.globemed.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Billing belongs to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    // One billing may have one insurance claim
    @OneToOne(mappedBy = "billing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private InsuranceClaim insuranceClaim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    // Enum for Billing Status
    public enum Status {
        PAID,
        PENDING,
        REJECTED
    }

    // Constructors
    public Billing() {}

    public Billing(Patient patient, Double amount, LocalDate date, Status status) {
        this.patient = patient;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public InsuranceClaim getInsuranceClaim() { return insuranceClaim; }
    public void setInsuranceClaim(InsuranceClaim insuranceClaim) {
        this.insuranceClaim = insuranceClaim;
        if (insuranceClaim != null) {
            insuranceClaim.setBilling(this);
        }
    }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
