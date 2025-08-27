package com.jiat.globemed.model;

import jakarta.persistence.*;

@Entity
@Table(name = "insurance_claims")
public class InsuranceClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One claim corresponds to one billing
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id", nullable = false, unique = true)
    private Billing billing;

    @Column(nullable = false, length = 100)
    private String insuranceProvider;

    @Column(nullable = false, length = 50)
    private String policyNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClaimStatus claimStatus;

    // Enum for Claim Status
    public enum ClaimStatus {
        SUBMITTED,
        APPROVED,
        REJECTED,
        PROCESSING
    }

    // Constructors
    public InsuranceClaim() {}

    public InsuranceClaim(Billing billing, String insuranceProvider, String policyNumber, ClaimStatus claimStatus) {
        this.billing = billing;
        this.insuranceProvider = insuranceProvider;
        this.policyNumber = policyNumber;
        this.claimStatus = claimStatus;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Billing getBilling() { return billing; }
    public void setBilling(Billing billing) {
        this.billing = billing;
        if (billing != null && billing.getInsuranceClaim() != this) {
            billing.setInsuranceClaim(this);
        }
    }

    public String getInsuranceProvider() { return insuranceProvider; }
    public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public ClaimStatus getClaimStatus() { return claimStatus; }
    public void setClaimStatus(ClaimStatus claimStatus) { this.claimStatus = claimStatus; }
}
