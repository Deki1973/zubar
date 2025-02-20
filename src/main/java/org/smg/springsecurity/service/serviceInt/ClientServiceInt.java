package org.smg.springsecurity.service.serviceInt;

import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.exception.ClientException;

import org.smg.springsecurity.repository.ClientRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ClientServiceInt extends ClientRepository{

    @Override
    List<Client> findAll();

    @Override
    Optional<Client> findById(Long id) throws ClientException;


    Client findClientByContact(String contact) throws ClientException;
    List<Client> findClientByFullNameStartingWithIgnoreCase(String fullName) throws ClientException;


    List<Client> findAllByOrderByFullNameAsc();





    @Modifying
    @Transactional
    @Query(nativeQuery=true,
            value="UPDATE client SET full_name=:fullName, contact=:contact, note=:note WHERE client_id=:clientId")
    int updateClient(Long clientId, String fullName, String contact, String note);

}
