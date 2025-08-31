package com.jiat.globemed.service;

class BasicUser implements UserRole {

    private String roleName;

    public BasicUser(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public boolean canAccess(String feature) {
        return false;
    }
}

abstract class RoleDecorator1 implements UserRole {

    protected UserRole decoratedUser;

    public RoleDecorator1(UserRole decoratedUser) {
        this.decoratedUser = decoratedUser;
    }

    @Override
    public String getRoleName() {
        return decoratedUser.getRoleName();
    }
}

class DoctorRole extends RoleDecorator1 {

    public DoctorRole(UserRole decoratedUser) {
        super(decoratedUser);
    }

    @Override
    public boolean canAccess(String feature) {
        return feature.equals("PR")
                || feature.equals("AS")
                || feature.equals("BI");
    }
}

class AdminRole extends RoleDecorator1 {

    public AdminRole(UserRole decoratedUser) {
        super(decoratedUser);
    }

    @Override
    public boolean canAccess(String feature) {
        return true;
    }
}

class PharmacistRole extends RoleDecorator1 {

    public PharmacistRole(UserRole decoratedUser) {
        super(decoratedUser);
    }

    @Override
    public boolean canAccess(String feature) {
        return feature.equals("PR");
    }
}


class NurseRole extends RoleDecorator1 {

    public NurseRole(UserRole decoratedUser) {
        super(decoratedUser);
    }

    @Override
    public boolean canAccess(String feature) {
        return feature.equals("PR");
    }
}

public class RoleManager {

    public static UserRole login(String role) {
        
        if (role.equals("ADMIN")) {
            return new AdminRole(new BasicUser("Admin"));
        } else if (role.equals("DOCTOR")) {
            return new DoctorRole(new BasicUser("Doctor"));
        } else if (role.equals("NURSE")) {
            return new NurseRole(new BasicUser("Nurse"));
        } else if (role.equals("PHARMACIST")) {
            return new PharmacistRole(new BasicUser("Pharmacist"));
        } 
        return null;
    }
}
