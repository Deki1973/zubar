package org.smg.springsecurity.repository;


import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.exception.ClientException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /*
    Client findClientByContact(String contact) throws ClientException;

    Client findClientByFullName(String fullName) throws ClientException;

    List<Client> findAllByOrderByClientIdAsc();
    List<Client> findAllByOrderByFullNameAsc();
*/
/*
    @Modifying
    @Transactional
    @Query(nativeQuery=true,
            value="UPDATE client SET full_name=:fullName, contact=:contact WHERE client_id=:clientId")
    void updateTesting2(Long clientId, String fullName, String contact);
*/
    /* sve izkomentarisano je prebaceno u interfejs
    * servisi i kontroleri su odgovarajuce prepravljeni */
}
