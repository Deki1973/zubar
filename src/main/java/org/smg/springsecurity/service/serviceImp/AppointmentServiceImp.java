package org.smg.springsecurity.service.serviceImp;

import org.smg.springsecurity.dto.AppointmentDto;
import org.smg.springsecurity.exception.AppointmentException;
import org.smg.springsecurity.exception.ClientException;
import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.model.Appointment;
import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.repository.AppointmentRepository;
import org.smg.springsecurity.service.serviceInt.AppointmentServiceInt;
import org.smg.springsecurity.service.serviceInt.ClientServiceInt;
import org.smg.springsecurity.service.serviceInt.DentistServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImp {

    @Autowired
    private AppointmentServiceInt appointmentServiceInt;

    @Autowired
    private DentistServiceInt dentistServiceInt;

    @Autowired
    private ClientServiceInt clientServiceInt;


    public ResponseEntity<List<Appointment>> getAll(){
        System.out.println("pozvan je servis getAll...");
        return new ResponseEntity<>(appointmentServiceInt.findAll(),HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<Optional<Appointment>> getById(Long id) throws AppointmentException {
        try {
            if (appointmentServiceInt.findById(id).isEmpty()) {
                throw new AppointmentException("There is no Appointment id: " + id, HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null, HttpStatus.valueOf(204));
        }
        return new ResponseEntity<>(appointmentServiceInt.findById(id), HttpStatus.valueOf(200));
    }

    public Appointment addNewAppointment(AppointmentDto appointmentDto, Long clientId, Long dentistId){
        System.out.println("pozvan je servis addNewAppointment - stara varijanta");

        Appointment newApp=new Appointment();
        Client foundClient=clientServiceInt.findById(clientId).orElseThrow(()->new ClientException("Client not found", (HttpStatus) HttpStatusCode.valueOf(204)));
        //ResponseEntity<Optional<Dentist>> foundClient=dentistServiceImp.getById(clientId);
        Dentist foundDentist=dentistServiceInt.findById(dentistId).orElseThrow(()->new DentistException("Dentist not found", (HttpStatus) HttpStatusCode.valueOf(204)));
        //ResponseEntity<Optional<Dentist>> foundDentist=dentistServiceImp.getById(dentistId);
        String newDescription=appointmentDto.getDescription();
        Date newAppointmentDateAndTime=appointmentDto.getAppointmentDateAndTime();
        Boolean newCompleted=appointmentDto.getCompleted();

        newApp.setDescription(newDescription);
        newApp.setClient(foundClient);
        newApp.setDentist(foundDentist);
        newApp.setAppointmentDateAndTime(newAppointmentDateAndTime);
        newApp.setCompleted(newCompleted);

        return appointmentServiceInt.save(newApp);

    }



    public ResponseEntity<List<Appointment>> findByClientId(Long clientId) throws ClientException{
        System.out.println("pozvan je servis findByClientId...");
        try {
            if (clientServiceInt.findById(clientId).isEmpty()) {
                throw new ClientException("There is no client id: " + clientId, HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(appointmentServiceInt.findByClientId(clientId),HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<List<Appointment>> findByDentistId(Long dentistId) throws DentistException{
        System.out.println("pozvan je servis findByDentistId");
        try {
            if (dentistServiceInt.findById(dentistId).isEmpty()) {
                throw new DentistException("There is no dentist id: " + dentistId, HttpStatusCode.valueOf(204));
            }
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(appointmentServiceInt.findByDentistId(dentistId),HttpStatusCode.valueOf(200));
    }

    public Appointment addNewAppointmentVar2(AppointmentDto appointmentDto){
        System.out.println("pozvan je servis add...Var2...");

        Appointment newAppointment=new Appointment();

        Long appointmentId=appointmentDto.getAppointmentId();
        String description=appointmentDto.getDescription();
        Long clientId=appointmentDto.getClientId();
        Long dentistId=appointmentDto.getDentistId();
        Boolean completed=appointmentDto.getCompleted();
        Date dateAndTimeScheduled=appointmentDto.getAppointmentDateAndTime();

        Client foundClient=clientServiceInt.findById(clientId).orElseThrow(()->new ClientException("no client id"+clientId, HttpStatus.valueOf(204)));
        Dentist foundDentist=dentistServiceInt.findById(dentistId).orElseThrow(()->new DentistException("no dentist id "+dentistId, HttpStatus.valueOf(204)));

        newAppointment.setAppointmentId(appointmentId);
        newAppointment.setDescription(description);
        newAppointment.setClient(foundClient);
        newAppointment.setDentist(foundDentist);
        newAppointment.setAppointmentDateAndTime(dateAndTimeScheduled);
        newAppointment.setCompleted(completed);

        return appointmentServiceInt.save(newAppointment);

    }

    public ResponseEntity<String> updateAppointment(Long id, AppointmentDto appointmentDto) throws AppointmentException{
        System.out.println("pozvan je servis updateAppointment");

            if (appointmentServiceInt.findById(id).isEmpty()) {
                throw new AppointmentException("There is no appointment id: " + id, HttpStatusCode.valueOf(204));
            }


        Long appointmentId=appointmentDto.getAppointmentId();
        String newDescription=appointmentDto.getDescription();
        Date newDateAndTime=appointmentDto.getAppointmentDateAndTime();
        Long newClientId=appointmentDto.getClientId();
        Long newDentistId=appointmentDto.getDentistId();
        Boolean newCompletedVal=appointmentDto.getCompleted();

        if(clientServiceInt.findById(newClientId).isEmpty()){
            throw new ClientException("There is no client id: "+newClientId,HttpStatusCode.valueOf(204));
        }

        if(dentistServiceInt.findById(newDentistId).isEmpty()){
            throw new DentistException("There is no dentist id: "+newDentistId,HttpStatusCode.valueOf(204));
        }

        //int rowsAffected=appointmentServiceInt.updateAppointment(newDateAndTime,newDescription,newClientId,newDentistId,newCompletedVal,appointmentId);
        int rowsAffected=appointmentServiceInt.updateAppointment(newDateAndTime,newClientId,newDentistId,newDescription, newCompletedVal,appointmentId);
        if (rowsAffected==0){
            return new ResponseEntity<String>("Oops! Something went wrong.",HttpStatusCode.valueOf(204));
        }else {

            return new ResponseEntity<>("Rows affected: "+rowsAffected, HttpStatusCode.valueOf(200));
        }
    }
}
