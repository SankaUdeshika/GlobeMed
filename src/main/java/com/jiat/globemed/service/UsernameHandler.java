package com.jiat.globemed.service;

import com.jiat.globemed.model.Staff;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.jiat.globemed.util.HibernateUtil;

public class UsernameHandler extends LoginHandler {

    @Override
    public boolean handle(Staff staff, String inputPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Staff> query = session.createQuery("FROM Staff WHERE username = :uname", Staff.class);
            query.setParameter("uname", staff.getUsername());
            Staff found = query.uniqueResult();
            if (found == null) {
                System.out.println("Username not found!");
                return false;
            }
            staff.setId(found.getId()); // store ID for next steps
            staff.setPassword(found.getPassword()); // hashed password from DB
            staff.setRole(found.getRole());
        }

        if (next != null) return next.handle(staff, inputPassword);
        return true;
    }
}
