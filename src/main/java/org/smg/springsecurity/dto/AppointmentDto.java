package org.smg.springsecurity.dto;


import java.util.Date;

public class AppointmentDto {

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    private Long appointmentId;
    private String description;
    private Long clientId;
    private Long dentistId;
    private Boolean completed;

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(Date appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
    }

    private Date appointmentDateAndTime;

    public AppointmentDto(){}

    public AppointmentDto(Long appointmentId, String description, Long clientId, Long dentistId, Date appointmentDateAndTime, Boolean completed) {
        this.appointmentId = appointmentId;
        this.description = description;
        this.clientId = clientId;
        this.dentistId = dentistId;
        this.appointmentDateAndTime=appointmentDateAndTime;
        this.completed=completed;

    }

    @Override
    public String toString() {
        return "AppointmentTwo{" +
                "appointmentId=" + appointmentId +
                ", description='" + description + '\'' +
                ", clientId=" + clientId +
                ", dentistId=" + dentistId +
                '}';
    }


}
