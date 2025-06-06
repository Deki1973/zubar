package org.smg.springsecurity.service.serviceImp;



import org.smg.springsecurity.dto.Response1;
import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.service.serviceInt.DentistServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImp  {

    @Autowired
    private DentistServiceInt dentistServiceInt;

    public List<Dentist> getAll(){
        System.out.println("pozvan je servis findAll...");
        return dentistServiceInt.findAllByOrderByFullNameAsc();
    }

    public ResponseEntity<Optional<Dentist>> getById(Long id) throws DentistException{
        System.out.println("pozven je servis getById...");
        try {
            if (dentistServiceInt.existsById(id) == false) {
                throw new DentistException("No dentist ID " + id + ".", HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(dentistServiceInt.findById(id), HttpStatus.valueOf(200));
    }

    public ResponseEntity<List<Dentist>> getByFullName(String fullName) throws DentistException{
        System.out.println("pozvan je servis getByFullName..");
        try {
            if (dentistServiceInt.findDentistByFullNameStartingWithIgnoreCase(fullName).isEmpty()) {
                throw new DentistException("No dentist" + fullName + ".", HttpStatus.valueOf(204));

            }
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.valueOf(204));
        }
        return new ResponseEntity<>(dentistServiceInt.findDentistByFullNameStartingWithIgnoreCase(fullName), HttpStatus.valueOf(200));
    }

    public Dentist addNew(Dentist dentist){
        System.out.println("pozvan je servis addnew..");
        return dentistServiceInt.save(dentist);
    }

    public ResponseEntity<String> removeDentist(Long id) throws DentistException{
        try {
            if (dentistServiceInt.findById(id).isEmpty()) {
                throw new DentistException("There is no dentist id: " + id, HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
        }
        System.out.println("pozvan je servis removeDentist...");
        dentistServiceInt.deleteById(id);
        return new ResponseEntity<>("Metoda uspesno izvrsena.",HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<Response1> updateDentist(Long id, Dentist dentist){

        Response1 response1=new Response1();
        try{
            if(dentistServiceInt.findById(id).isEmpty()){
                throw new DentistException("No dentist id: "+id,HttpStatusCode.valueOf(204));
            }
            Long dentistId=id;
            String fullName=dentist.getFullName();
            String contact=dentist.getContact();
            int rowsAffected=dentistServiceInt.updateDentist(dentistId,fullName,contact);
            System.out.println("rows affected: "+rowsAffected);
            if(rowsAffected!=1){
                throw new DentistException("Oops! Something went wrong. Contact your administrator.",HttpStatusCode.valueOf(500));
            }
            response1.setRetCode(200);
            response1.setMessage("Rows affected: 1");
            return new ResponseEntity<Response1>(response1,HttpStatusCode.valueOf(200));


        }catch (DentistException ex){
            response1.setRetCode(204);
            response1.setMessage(ex.getMessage());
            return new ResponseEntity<Response1>(response1,HttpStatusCode.valueOf(204));
        }


    }

    /*
    public Optional<Dentist> updateDentist(Long id, Dentist dentist){
        System.out.println("pozvan je servis updateDentist");
        Long dentistId=id;
        String fullName=dentist.getFullName();
        String contact=dentist.getContact();
        int rowsAffected=dentistServiceInt.updateDentist(dentistId,fullName,contact);
        System.out.println("rows affected: "+rowsAffected);
        return dentistServiceInt.findById(dentistId);

    }

     */

    public ResponseEntity<Optional<Dentist>> getByContact(String contact){
        if (dentistServiceInt.findDentistByContact(contact).isEmpty()){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(dentistServiceInt.findDentistByContact(contact),HttpStatusCode.valueOf(200));
    }


}
