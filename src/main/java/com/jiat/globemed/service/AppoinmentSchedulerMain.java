package com.jiat.globemed.service;

import com.jiat.globemed.dao.AppoinmentDAO;
import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.model.Staff;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface AppointmentMediator {

    boolean scheduleAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time);

    boolean updateAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time);

}

public class AppoinmentSchedulerMain {

    public boolean scheduleAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time) {
        AppointmentMediator scheduler = new AppointmentScheduler();
        boolean isSchedule = scheduler.scheduleAppointment(patient, doctor, date, time);

        return isSchedule;
    }

    public boolean updateAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time) {
        AppointmentMediator scheduler = new AppointmentScheduler();
        boolean isSchedule = scheduler.updateAppointment(patient, doctor, date, time);
        return isSchedule;
    }
}

class AppointmentScheduler implements AppointmentMediator {

    private List<Appointment> appointments = new AppoinmentDAO().getAllAppointments();

    @Override
    public boolean scheduleAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time) {
        // check if doctor is free at this date/time
        for (Appointment appt : appointments) {
            System.out.println(appt.getId());

            if (appt.getDoctorId().equals(doctor.getId()) && appt.getDate().equals(date) && appt.getTime().equals(time)) {
                System.out.println("Doctor not available at this time!");
                return false;
            }
        }
        // create appointment
        Appointment newAppt = new Appointment(patient, doctor, date, time, Appointment.Status.SCHEDULED);
        appointments.add(newAppt);
        new AppoinmentDAO().saveAppoinment(newAppt);
        System.out.println("Appointment scheduled for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + date + " " + time);
        return true;
    }

    @Override
    public boolean updateAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time) {
        // check if doctor is free at this date/time
        for (Appointment appt : appointments) {
        

            if (appt.getDoctorId().equals(doctor.getId()) && appt.getDate().equals(date) && appt.getTime().equals(time)) {
                System.out.println("Doctor not available at this time!");
                return false;
            }
        }
        // create appointment
        Appointment newAppt = new Appointment(patient, doctor, date, time, Appointment.Status.SCHEDULED);
        appointments.add(newAppt);
        new AppoinmentDAO().updateAppoinment(newAppt);
        System.out.println("Appointment Updated for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + date + " " + time);
        return true;
    }

}
