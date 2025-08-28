package com.jiat.globemed.service;

import com.jiat.globemed.model.Appointment;
import com.jiat.globemed.model.Patient;
import com.jiat.globemed.model.Staff;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

interface AppointmentMediator {
    boolean scheduleAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time);
}

public class AppointmentScheduler {

    private List<Appointment> appointments = new ArrayList<>();

    public boolean scheduleAppointment(Patient patient, Staff doctor, LocalDate date, LocalTime time) {
        // check if doctor is free at this date/time
        for (Appointment appt : appointments) {
            if (appt.getDoctor().equals(doctor) && appt.getDate().equals(date) && appt.getTime().equals(time)) {
                System.out.println("Doctor not available at this time!");
                return false;
            }
        }

        // create appointment
        Appointment newAppt = new Appointment(patient, doctor, date, time, Appointment.Status.SCHEDULED);
        appointments.add(newAppt);

        System.out.println("Appointment scheduled for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + date + " " + time);
        return true;
    }

}
