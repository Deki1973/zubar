package org.smg.springsecurity.controller;



import org.smg.springsecurity.dto.AppointmentDto2;
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
@CrossOrigin
@RequestMapping("/appointment")
public class AppointmentController {

    //@Autowired
    private final AppointmentServiceImp appointmentServiceImp;
    public AppointmentController(AppointmentServiceImp appointmentServiceImp){
        this.appointmentServiceImp=appointmentServiceImp;
    }



    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/client/{clientId}/dentist/{dentistId}")
    @ResponseBody
    public Appointment addNewAppointment(@RequestBody AppointmentDto appointmentDto, @PathVariable Long clientId, @PathVariable Long dentistId) throws Exception {
        System.out.println("pozvan je kontroler addAppointment");

        return appointmentServiceImp.addNewAppointment(appointmentDto,clientId,dentistId);


    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseBody
    public Appointment addNewAppointmentVar2(@RequestBody AppointmentDto appointmentDto) throws Exception {
        System.out.println("pozvan je kontroler ");
        return appointmentServiceImp.addNewAppointmentVar2(appointmentDto);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getall")
    @ResponseBody
    public ResponseEntity<List<Appointment>> getAll(){
        //return appointmentRepository.findAll();
        return appointmentServiceImp.getAll();


    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/client/{id}/")
    @ResponseBody
    public ResponseEntity<List<Appointment>> getByClientIdError(@PathVariable Long id){

        return appointmentServiceImp.findByClientId(id);



    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/client/{id}")
    @ResponseBody
    public ResponseEntity<List<Appointment>> getByClientId(@PathVariable Long id) {

        return appointmentServiceImp.findByClientId(id);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/dentist/{id}")
    @ResponseBody
    public ResponseEntity<List<Appointment>> getByDentistId(@PathVariable Long id){
        //return appointmentRepository.findByDentistId(id);
        return appointmentServiceImp.findByDentistId(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Appointment>> getById(@PathVariable Long id){

        return appointmentServiceImp.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto){
        System.out.println("pozvan je kontroler update appointment...");
        return appointmentServiceImp.updateAppointment(id,appointmentDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/getExact")
    @ResponseBody
    public ResponseEntity<Optional<Appointment>> getExact(@RequestBody AppointmentDto2 appointmentDto2){
        // iako pribavljamo podatke, moramo koristiti anotaciju @Post
        // jer @Get ne podrzava body
        return appointmentServiceImp.getExact(appointmentDto2);

    }



}
