package org.smg.springsecurity.service.serviceInt;

import org.smg.springsecurity.dto.AppointmentDto;
import org.smg.springsecurity.exception.AppointmentException;
import org.smg.springsecurity.exception.ClientException;
import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.model.Appointment;
import org.smg.springsecurity.repository.AppointmentRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentServiceInt extends AppointmentRepository {




    @Query(
            nativeQuery = true,
            value = "SELECT * FROM public.appointment pa WHERE pa.client_id=:clientId"
    )
    List<Appointment> findByClientId(Long clientId) throws ClientException;
    // pri cemu je public - naziv scheme


    @Query(
            nativeQuery = true,
            value = "SELECT * FROM public.appointment pa WHERE pa.dentist_id=:dentistId"
    )
    List<Appointment> findByDentistId(Long dentistId) throws DentistException;


    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "UPDATE appointment SET appointment_date_and_time=:newAppointmentDateAndTime, client_id=:clientId, dentist_id=:dentistId,description=:newDescription, completed=:completedVal, price=:priceVal WHERE appointment_id=:appointmentId")
    int updateAppointment(Date newAppointmentDateAndTime, Long clientId, Long dentistId, String newDescription, Boolean completedVal, Long priceVal, Long appointmentId) throws AppointmentException;




}
