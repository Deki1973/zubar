package org.smg.springsecurity.repository;


import org.smg.springsecurity.model.Appointment;
import org.smg.springsecurity.exception.AppointmentException;
import org.smg.springsecurity.exception.ClientException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


}
