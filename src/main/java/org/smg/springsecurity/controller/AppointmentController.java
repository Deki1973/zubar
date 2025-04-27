package org.smg.springsecurity.controller;



import com.sun.tools.jconsole.JConsoleContext;
import com.sun.tools.jconsole.JConsolePlugin;
import org.apache.coyote.Response;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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
    public ResponseEntity<Appointment> addNewAppointmentVar2(@RequestBody AppointmentDto appointmentDto) throws Exception {
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id){
        System.out.println("pozvan je kontroler delete appointment...");
        return appointmentServiceImp.deleteAppointment(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/client/{clientId}/dentist/{dentistId}/date/{appointmentDateAndTime}")
    @ResponseBody
    public ResponseEntity<Optional<Appointment>> getAppByMultipleParams(@PathVariable Long clientId, @PathVariable Long dentistId, @PathVariable String appointmentDateAndTime){
        System.out.println("pozvan je kontroler getAppByMultipleVars...");
        System.out.println("appointmentDateAndTime: "+appointmentDateAndTime);


        return appointmentServiceImp.getApointmentByClientAndDentistAndDate(clientId,dentistId,appointmentDateAndTime);


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
