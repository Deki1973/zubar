package org.smg.springsecurity.model;


import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Long appointmentId;

    private String description;

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Client getClient(){
        return this.client;
    }

    public void setClient(Client client){
        this.client=client;
    }

    @Column(name = "appointment_date_and_time")
    private Date appointmentDateAndTime;

    public Date getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(Date appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    private Boolean completed;

    @ManyToOne
    @JoinColumn(name="dentist_id")
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;



    public Appointment(){}



    public Appointment(String description)
    {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }


}
