package org.smg.springsecurity.service.serviceImp;


import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.exception.ClientException;
import org.smg.springsecurity.repository.ClientRepository;
import org.smg.springsecurity.service.serviceInt.ClientServiceInt;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Service
public class ClientServiceImp{

   @Autowired
    private ClientServiceInt clientServiceInt;

    public List<Client> findAll(){
        return clientServiceInt.findAll();
    }

    public ResponseEntity<Optional<Client>> getById(Long id) throws ClientException{
        try{
            if(clientServiceInt.findById(id).isEmpty()){
                throw new ClientException("No client id: "+id+".", HttpStatus.valueOf(204));
            }

        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.valueOf(204));
        }

        return new ResponseEntity<>(clientServiceInt.findById(id), HttpStatus.valueOf(200));
    }

    public ResponseEntity<Client> findClientByContact(String contact) throws ClientException{

        //return clientServiceInt.findClientByContact(contact);
        return new ResponseEntity<>(clientServiceInt.findClientByContact(contact),HttpStatus.valueOf(200));
    }


    public ResponseEntity<Client> findClientByFullName(String fullName) throws ClientException{
        return new ResponseEntity<>(clientServiceInt.findClientByFullName(fullName),HttpStatus.valueOf(200));
    }

    public ResponseEntity<List<Client>> findAllByOrderByFullNameAsc(){
        return new ResponseEntity<>(clientServiceInt.findAllByOrderByFullNameAsc(),HttpStatus.valueOf(200));
    }


    public int updateClient(Long clientId, String fullName, String contact, String note) {
        System.out.println(fullName + " | " + contact + " | " + clientId);

            if (clientServiceInt.findById(clientId).isEmpty()) {
                throw new ClientException("No client id: " + clientId, HttpStatus.valueOf(204));
            }

        return clientServiceInt.updateClient(clientId,fullName,contact,note);

    }

    public ResponseEntity<Client> addClient(Client client){
        System.out.println("pozvan je servis addClient...");
        return new ResponseEntity<>(clientServiceInt.save(client), HttpStatus.valueOf(200));
    }

    public ResponseEntity<String> removeClientById(Long id){
        System.out.println("pozvan je servis deleteClientById...");
        try{
            if(clientServiceInt.findById(id).isEmpty()){
                throw new ClientException("Client id "+id+" not found.", HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>("There is no client id: "+id, HttpStatusCode.valueOf(204));
        }
        clientServiceInt.deleteById(id);
        return new ResponseEntity<>("Method executed.", HttpStatusCode.valueOf(200));
    }

}
