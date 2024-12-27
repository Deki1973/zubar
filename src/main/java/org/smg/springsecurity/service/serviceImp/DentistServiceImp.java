package org.smg.springsecurity.service.serviceImp;



import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.service.serviceInt.DentistServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
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
        return dentistServiceInt.findAll();
    }

    public ResponseEntity<Optional<Dentist>> getById(Long id) throws DentistException{
        System.out.println("pozven je servis getById...");
        return new ResponseEntity<>(dentistServiceInt.findById(id), HttpStatus.valueOf(200));
    }

    public ResponseEntity<Optional<Dentist>> getByFullName(String fullName) throws DentistException{
        System.out.println("pozvan je servis getByFullName..");
        try {
            if (dentistServiceInt.findDentistByFullName(fullName).isEmpty()) {
                throw new DentistException("No dentist" + fullName + ".", HttpStatus.valueOf(204));

            }
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.valueOf(204));
        }
        return new ResponseEntity<>(dentistServiceInt.findDentistByFullName(fullName), HttpStatus.valueOf(200));
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

    public Optional<Dentist> updateDentist(Long id, Dentist dentist){
        System.out.println("pozvan je servis updateDentist");
        Long dentistId=id;
        String fullName=dentist.getFullName();
        String contact=dentist.getContact();
        int rowsAffected=dentistServiceInt.updateDentist(dentistId,fullName,contact);
        System.out.println("rows affected: "+rowsAffected);
        return dentistServiceInt.findById(dentistId);

    }


}
