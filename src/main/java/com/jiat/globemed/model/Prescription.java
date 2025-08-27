package com.jiat.globemed.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "prescriptions")
public class Prescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many prescriptions belong to one patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Many prescriptions belong to one doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // A prescription can have multiple medications
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "prescription_medications",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private Set<Medication> medications = new HashSet<>();

    @Column(length = 1000)
    private String instructions; // e.g., dosage, timing, notes

    // Constructors
    public Prescription() {}

    public Prescription(Patient patient, Doctor doctor, Set<Medication> medications, String instructions) {
        this.patient = patient;
        this.doctor = doctor;
        this.medications = medications;
        this.instructions = instructions;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public Set<Medication> getMedications() { return medications; }
    public void setMedications(Set<Medication> medications) { this.medications = medications; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    // Utility methods
    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.getPrescriptions().add(this);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.getPrescriptions().remove(this);
    }
}