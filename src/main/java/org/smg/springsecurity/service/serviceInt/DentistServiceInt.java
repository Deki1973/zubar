package org.smg.springsecurity.service.serviceInt;



import org.smg.springsecurity.model.Dentist;

import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DentistServiceInt extends DentistRepository {



    @Query(nativeQuery = true,
            value = "SELECT dentist_id, full_name, contact FROM dentist WHERE dentist.full_name=:fullName")
    Optional<Dentist> findDentistByFullName(String fullName) throws DentistException;

    List<Dentist> findAllByOrderByFullNameAsc();




    @Query(nativeQuery = true,
            value = "SELECT contact FROM dentist WHERE dentist.dentist_id=:dentistId"
    )
    String findContactById(Long dentistId) throws DentistException;

  

    @Modifying
    @Transactional
    @Query(nativeQuery=true,
            value="UPDATE dentist SET full_name=:fullName, contact=:contact WHERE dentist_id=:dentistId")
    int updateDentist(Long dentistId, String fullName, String contact);

}
