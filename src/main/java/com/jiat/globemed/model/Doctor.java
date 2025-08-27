package com.jiat.globemed.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100, nullable = false)
    private String specialization;

    @Column(length = 15, unique = true, nullable = false)
    private String phone;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    // One doctor can have many appointments (schedule)
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Appointment> schedule = new ArrayList<>();

    // Constructors
    public Doctor() {}

    public Doctor(String name, String specialization, String phone, String email) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Appointment> getSchedule() { return schedule; }
    public void setSchedule(List<Appointment> schedule) { this.schedule = schedule; }

    // Utility methods
    public void addAppointment(Appointment appointment) {
        schedule.add(appointment);
        appointment.setDoctor(this);
    }

    public void removeAppointment(Appointment appointment) {
        schedule.remove(appointment);
        appointment.setDoctor(null);
    }
}
