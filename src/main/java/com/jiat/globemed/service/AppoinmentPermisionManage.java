package com.jiat.globemed.service;

import java.util.ArrayList;
import java.util.List;


public class AppoinmentPermisionManage {
    private static RoleComposite adminRole;
    private static RoleComposite doctorRole;
    private static RoleComposite nurseRole;
    private static RoleComposite pharmacistRole;
   static {
       PermissionComponent add = new PermissionLeaf("ADD_APPOINTMENT");
       PermissionComponent view = new PermissionLeaf("VIEW_APPOINTMENT");
       PermissionComponent update = new PermissionLeaf("UPDATE_APPOINTMENT");
       PermissionComponent delete = new PermissionLeaf("DELETE_APPOINTMENT");

       adminRole = new RoleComposite("ADMIN");
       adminRole.add(add);
       adminRole.add(view);
       adminRole.add(update);
       adminRole.add(delete);

       doctorRole = new RoleComposite("DOCTOR");
       doctorRole.add(add);
       doctorRole.add(view);
       doctorRole.add(update);

       nurseRole = new RoleComposite("NURSE");
       nurseRole.add(view);
       nurseRole.add(update);

       pharmacistRole = new RoleComposite("PHARMACIST");
       pharmacistRole.add(view);

   }

    public static PermissionComponent getRolePermissions(String role) {
        switch (role) {
            case "ADMIN":
                return adminRole;
            case "DOCTOR":
                return doctorRole;
            case "NURSE":
                return nurseRole;
            case "PHARMACIST":
                return pharmacistRole;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}



class PermissionLeaf implements PermissionComponent {
    private String permission;

    public PermissionLeaf(String permission) {
        this.permission = permission;
    }

    @Override
    public void add(PermissionComponent component) {
        throw new UnsupportedOperationException("Leaf cannot add child");
    }

    @Override
    public void remove(PermissionComponent component) {
        throw new UnsupportedOperationException("Leaf cannot remove child");
    }

    @Override
    public void showPermissions() {
        System.out.println("- " + permission);
    }

    @Override
    public boolean hasPermission(String action) {
        return permission.equalsIgnoreCase(action);
    }
}



class RoleComposite implements PermissionComponent {
    private String roleName;
    private List<PermissionComponent> permissions = new ArrayList<>();

    public RoleComposite(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public void add(PermissionComponent component) {
        permissions.add(component);
    }

    @Override
    public void remove(PermissionComponent component) {
        permissions.remove(component);
    }

    @Override
    public void showPermissions() {
        System.out.println(roleName + " permissions:");
        for (PermissionComponent p : permissions) {
            p.showPermissions();
        }
    }

    @Override
    public boolean hasPermission(String action) {
        for (PermissionComponent p : permissions) {
            if (p.hasPermission(action)) return true;
        }
        return false;
    }
}


