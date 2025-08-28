package com.jiat.globemed.dao;

import com.jiat.globemed.model.Patient;
import com.jiat.globemed.model.Staff;
import com.jiat.globemed.util.HibernateUtil;
import org.hibernate.Session;

public class StafDAO {
    // Search by ID
    public Staff findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Staff staff = session.get(Staff.class, id);
            if(staff != null) {
                return staff;
            }else{
                return null;
            }
        }
    }

}
