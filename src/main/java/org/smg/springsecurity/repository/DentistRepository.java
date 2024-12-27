package org.smg.springsecurity.repository;



import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.exception.DentistException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {
    //public List<Dentist> findByFullName(String fullName) throws DentistException;

    //Dentist findDentistByContact(String contact) throws DentistException;

    /*
    @Query(
            nativeQuery = true,
            value = "SELECT dd.dentist_id, dd.full_name, dd.contact, da.client_id, da.description FROM dentist.dentist dd WHERE dd.full_name=:fullName"
    )
    otkud ovo ovde?
     */
    // probacemo sa @Query notacijom:

/*
    @Query(nativeQuery = true,
            value = "SELECT dentist_id, full_name, contact FROM dentist WHERE dentist.full_name=:fullName")
    Dentist findDentistByFullName(String fullName) throws DentistException;

    List<Dentist> findAllByOrderByFullNameAsc();




    @Query(nativeQuery = true,
            value = "SELECT contact FROM dentist WHERE dentist.dentist_id=:dentistId"
    )
    String findContactById(Long dentistId) throws DentistException;
*/

    /*
    @Modifying
    @Transactional
    @Query(nativeQuery=true,
            value="UPDATE dentist SET full_name=:fullName, contact=:contact WHERE dentist_id=:dentistId")
    void updateTesting1(Long dentistId, String fullName, String contact);

*/

}
