package com.jiat.globemed.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    // Many medications can belong to many prescriptions
    @ManyToMany(mappedBy = "medications", fetch = FetchType.LAZY)
    private Set<Prescription> prescriptions = new HashSet<>();

    // Constructors
    public Medication() {}

    public Medication(String name, String description, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Set<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(Set<Prescription> prescriptions) { this.prescriptions = prescriptions; }

    // Utility methods
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        prescription.getMedications().add(this);
    }

    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
        prescription.getMedications().remove(this);
    }
}
