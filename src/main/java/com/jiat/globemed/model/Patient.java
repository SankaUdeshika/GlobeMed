package com.jiat.globemed.model;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dob;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 255)
    private String address;

    @Column(length = 15, unique = true)
    private String phone;

    @Column(length = 100, unique = true)
    private String email;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String medicalHistory; // Can be JSON or plain text

    // Relationship: One patient can have many treatment plans
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TreatmentPlan> treatmentPlans = new ArrayList<>();

    // Constructors
    public Patient() {}

    public Patient(String name, Date dob, String gender, String address, String phone, String email, String medicalHistory) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.medicalHistory = medicalHistory;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public List<TreatmentPlan> getTreatmentPlans() { return treatmentPlans; }
    public void setTreatmentPlans(List<TreatmentPlan> treatmentPlans) { this.treatmentPlans = treatmentPlans; }

    // Utility methods
    public void addTreatmentPlan(TreatmentPlan plan) {
        treatmentPlans.add(plan);
        plan.setPatient(this);
    }

    public void removeTreatmentPlan(TreatmentPlan plan) {
        treatmentPlans.remove(plan);
        plan.setPatient(null);
    }
}
