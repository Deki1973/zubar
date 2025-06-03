package org.smg.springsecurity.dto;


import lombok.ToString;

import java.util.Date;

public class AppointmentDto2 {
    private Long clientId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getDentistId() {
        return dentistId;
    }

    public void setDentistId(Long dentistId) {
        this.dentistId = dentistId;
    }

    public String getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(String appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
    }

    private Long dentistId;
    private String appointmentDateAndTime;

    public AppointmentDto2(){
    }
    public AppointmentDto2(Long clientId, Long dentistId, String appointmentDateAndTime){
        this.clientId=clientId;
        this.dentistId=dentistId;
        this.appointmentDateAndTime=appointmentDateAndTime;
    }

    @Override
    public String toString() {
        return "AppointmentDto2{" +
                "clientId=" + clientId +
                ", dentistId=" + dentistId +
                ", appointmentDateAndTime=" + appointmentDateAndTime +
                '}';
    }
}
