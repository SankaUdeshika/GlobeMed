package com.jiat.globemed.dao;

import com.jiat.globemed.model.Staff;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;

public class StafDAO {
    // Search by ID
    public Staff findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Staff.class, id);
        }
    }
}
