package org.smg.springsecurity.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dentist")
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dentist_id")
    private Long dentistId;

    private String fullName;
    private String contact;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "dentist"
    )
    private List<Appointment> appointmentList=new ArrayList<>();

    private List<Appointment> getAppointmentList(){
        return appointmentList;
    }


    public Dentist(){}

    public Dentist(String fullName, String contact) {
        this.fullName = fullName;
        this.contact = contact;
    }

    public Long getDentistId() {
        return dentistId;
    }

    public void setDentistId(Long dentistId) {
        this.dentistId = dentistId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString(){
        return "Denist: | ID: "+this.dentistId+" | Full Name: "+this.fullName+" | contact: "+this.contact+" \n "+"Appointments: "+this.appointmentList.get(1);
    }
}
