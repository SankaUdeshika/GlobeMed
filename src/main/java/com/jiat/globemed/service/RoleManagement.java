package com.jiat.globemed.service;


import com.jiat.globemed.gui.Dashboard;
import com.jiat.globemed.model.Staff;

import javax.swing.*;

interface RolePermission {
    void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3);

}

public class RoleManagement {
    public void RoleManagementProcess(JButton jButton1,JButton jButton2,JButton jButton3) {

        RolePermission permission = new BasePermission( jButton1, jButton2, jButton3);

        if (Dashboard.LoggedUserRole == Staff.Role.ADMIN || Dashboard.LoggedUserRole == Staff.Role.DOCTOR) {
            permission = new AdminDoctorPermission(permission);
        } else if (Dashboard.LoggedUserRole == Staff.Role.NURSE) {
            permission = new NursePermission(permission);
        } else if (Dashboard.LoggedUserRole == Staff.Role.PHARMACIST) {
            permission = new PharmacistPermission(permission);
        }

        permission.applyPermissions( jButton1, jButton2, jButton3);
    }
}

class BasePermission implements RolePermission {
    protected JButton jButton1, jButton2, jButton3;

    public BasePermission(JButton jButton1, JButton jButton2, JButton jButton3) {
        this.jButton1 = jButton1;
        this.jButton2 = jButton2;
        this.jButton3 = jButton3;
    }

    @Override
    public void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3) {
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
}


abstract class RoleDecorator implements RolePermission {
    protected RolePermission decoratedPermission;

    public RoleDecorator(RolePermission decoratedPermission) {
        this.decoratedPermission = decoratedPermission;
    }

    @Override
    public void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3) {
        decoratedPermission.applyPermissions( jButton1, jButton2, jButton3); // disable all first
    }
}


class AdminDoctorPermission extends RoleDecorator {
    public AdminDoctorPermission(RolePermission decoratedPermission) {
        super(decoratedPermission);
    }

    @Override
    public void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3) {
        super.applyPermissions(jButton1,jButton2,jButton3);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }
}

class NursePermission extends RoleDecorator {
    public NursePermission(RolePermission decoratedPermission) {
        super(decoratedPermission);
    }

    @Override
    public void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3) {
        super.applyPermissions(jButton1,jButton2,jButton3);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }
}

class PharmacistPermission extends RoleDecorator {
    public PharmacistPermission(RolePermission decoratedPermission) {
        super(decoratedPermission);
    }

    @Override
    public void applyPermissions(JButton jButton1,JButton jButton2,JButton jButton3) {
        super.applyPermissions(jButton1,jButton2,jButton3);
        jButton3.setEnabled(true);
    }
}

