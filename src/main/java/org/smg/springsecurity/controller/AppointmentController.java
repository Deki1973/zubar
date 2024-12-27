package org.smg.springsecurity.controller;



import org.smg.springsecurity.model.Appointment;
import org.smg.springsecurity.dto.AppointmentDto;
import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.exception.ClientException;
import org.smg.springsecurity.exception.DentistException;
import org.smg.springsecurity.repository.AppointmentRepository;
import org.smg.springsecurity.repository.ClientRepository;
import org.smg.springsecurity.repository.DentistRepository;
import org.smg.springsecurity.service.serviceImp.AppointmentServiceImp;
import org.smg.springsecurity.service.serviceImp.DentistServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    /*
    @Autowired
    private AppointmentRepository appointmentRepository;
*/
    @Autowired
    private AppointmentServiceImp appointmentServiceImp;

    /*
    @Autowired
    private DentistRepository dentistRepository;
*/
    /*
    @Autowired
    private ClientRepository clientRepository;
*/
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/client/{clientId}/dentist/{dentistId}")
    public Appointment addNewAppointment(@RequestBody AppointmentDto appointmentDto, @PathVariable Long clientId, @PathVariable Long dentistId) throws Exception {
        System.out.println("pozvan je kontroler addAppointment");

        return appointmentServiceImp.addNewAppointment(appointmentDto,clientId,dentistId);


    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/var2")
    public Appointment addNewAppointmentVar2(@RequestBody AppointmentDto appointmentDto) throws Exception {
        System.out.println("pozvan je kontroler ");
        return appointmentServiceImp.addNewAppointmentVar2(appointmentDto);
        /*
        premesteno u servis:
        AppointmentDto newAppDto=new AppointmentDto();

        Appointment newApp=new Appointment();
        newApp.setDescription(appointmentDto.getDescription());
        newApp.setClient(clientRepository.findById(appointmentDto.getClientId()).orElseThrow(()->new ClientException("Client not found",(HttpStatus) HttpStatusCode.valueOf(204))));
        newApp.setDentist(dentistRepository.findById(appointmentDto.getDentistId()).orElseThrow(()->new DentistException("Dentist not found",(HttpStatus) HttpStatusCode.valueOf(204))));
        newApp.setAppointmentDateTime(appointmentDto.getAppointmentDateAndTime());
        newApp.setCompleted(appointmentDto.getCompleted());
        System.out.println("appTwo client id: "+appointmentDto.getClientId());
        System.out.println("appTwo dentist id: "+appointmentDto.getDentistId());
        System.out.println("data and time is "+appointmentDto.getAppointmentDateAndTime());


        return appointmentRepository.save(newApp);

         */
    }

/*
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/update/{appointmentId}/dentist/{dentistId}/client/{clientId}")
    public Appointment updateAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long dentistId,
            @PathVariable Long clientId,
            @RequestBody Appointment appointmentIn
    ) throws Exception {

        Appointment appointmentFound=appointmentRepository.findById(appointmentId).orElseThrow(()->new Exception("No appointment ID: "+appointmentId));
        Dentist dentistFound=dentistRepository.findById(dentistId).orElseThrow(()->new Exception("No dentist with ID: "+dentistId));
        Client clientFound=clientRepository.findById(clientId).orElseThrow(()->new Exception("No client with ID: "+ clientId));


        appointmentFound.setDescription(appointmentIn.getDescription());
        appointmentFound.setDentist(dentistFound);
        appointmentFound.setClient(clientFound);
        appointmentFound.setCompleted(appointmentIn.getCompleted());

        appointmentRepository.save(appointmentFound);

        return appointmentRepository.findById(appointmentId).orElseThrow(()->new Exception("There is no appointment wiht ID: "+appointmentId));

    }

*/
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<List<Appointment>> getAll(){
        //return appointmentRepository.findAll();
        return appointmentServiceImp.getAll();


    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/client/{id}/")
    public ResponseEntity<List<Appointment>> getByClientIdError(@PathVariable Long id){

        return appointmentServiceImp.findByClientId(id);



    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<Appointment>> getByClientId(@PathVariable Long id) {

        return appointmentServiceImp.findByClientId(id);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/dentist/{id}")
    public ResponseEntity<List<Appointment>> getByDentistId(@PathVariable Long id){
        //return appointmentRepository.findByDentistId(id);
        return appointmentServiceImp.findByDentistId(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Appointment>> getById(@PathVariable Long id){

        return appointmentServiceImp.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto){
        System.out.println("pozvan je kontroler update appointment...");
        return appointmentServiceImp.updateAppointment(id,appointmentDto);
    }


}
