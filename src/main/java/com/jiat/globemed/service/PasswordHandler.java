package com.jiat.globemed.service;

import com.jiat.globemed.model.Staff;
import java.util.Objects;

public class PasswordHandler extends LoginHandler {

    @Override
    public boolean handle(Staff staff, String inputPassword) {
        // In real app, use hashing (BCrypt/Argon2)
        if (!Objects.equals(staff.getPassword(), inputPassword)) {
            System.out.println("Password incorrect!");
            return false;
        }

        if (next != null) return next.handle(staff, inputPassword);
        return true;
    }
}
