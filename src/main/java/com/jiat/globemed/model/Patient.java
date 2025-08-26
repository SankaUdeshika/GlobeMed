package com.jiat.globemed.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String contactNumber;
    private String email;

    @Column(columnDefinition="TEXT")
    private String medicalHistory;

    private String bloodGroup;
    private String insuranceProvider;
    private String insurancePolicyNumber;

    // constructors, getters setters omitted for brevity

    private Patient() {}
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String firstName, lastName;
        private LocalDate dob;
        private String gender, contactNumber, email, medicalHistory;
        private String bloodGroup, insuranceProvider, insurancePolicyNumber;

        public Builder firstName(String s){ this.firstName=s; return this; }
        public Builder lastName(String s){ this.lastName=s; return this; }
        public Builder dob(LocalDate d){ this.dob=d; return this; }
        public Builder gender(String g){ this.gender=g; return this; }
        public Builder contactNumber(String c){ this.contactNumber=c; return this; }
        public Builder email(String e){ this.email=e; return this; }
        public Builder medicalHistory(String m){ this.medicalHistory=m; return this; }
        public Builder bloodGroup(String b){ this.bloodGroup=b; return this; }
        public Builder insuranceProvider(String p){ this.insuranceProvider=p; return this; }
        public Builder insurancePolicyNumber(String n){ this.insurancePolicyNumber=n; return this; }

        public Patient build(){
            Patient p = new Patient();
            p.firstName=this.firstName; p.lastName=this.lastName;
            p.dob=this.dob; p.gender=this.gender; p.contactNumber=this.contactNumber;
            p.email=this.email; p.medicalHistory=this.medicalHistory;
            p.bloodGroup=this.bloodGroup; p.insuranceProvider=this.insuranceProvider;
            p.insurancePolicyNumber=this.insurancePolicyNumber;
            return p;
        }
    }
}
