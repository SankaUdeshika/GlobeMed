package com.jiat.globemed.service;

import com.jiat.globemed.model.Staff;
import com.jiat.globemed.model.Staff.Role;


public class RoleHandler extends LoginHandler {

    @Override
    public boolean handle(Staff staff, String inputPassword) {
        if (staff.getRole() == Role.ADMIN || staff.getRole() == Role.DOCTOR) {
            return true;
        } else {
            System.out.println("Role not allowed!");
            return false;
        }
    }
}
