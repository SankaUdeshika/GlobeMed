package com.jiat.globemed.service;

import com.jiat.globemed.model.Staff;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Login {

    private static Staff user;
    private static UserRole role;

    public UserRole getRole() {
        return role;
    }

    public Staff getUser() {
        return user;
    }

    public String login(String username, String password, com.jiat.globemed.gui.Login frame) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Staff> query = session.createQuery("FROM Staff WHERE username = :username", Staff.class);
            query.setParameter("username", username);
            Staff found = query.uniqueResult();
            if (found == null) {
                System.out.println("Invalid Username");
                return "Invalid Username";
            } else {
                if (found.getPassword().equals(password)) {
                    user = found;
                    System.out.println("Login Successful");

                    role = new RoleManager().login(found.getRole().name());
                    frame.dispose();
                    return "Success";

                } else {
                    System.out.println("Invalid Password");
                    return "Invalid Password";
                }
            }

        }
    }

}
