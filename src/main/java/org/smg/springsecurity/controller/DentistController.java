package org.smg.springsecurity.controller;



import org.smg.springsecurity.model.Dentist;
import org.smg.springsecurity.service.serviceImp.ClientServiceImp;
import org.smg.springsecurity.service.serviceImp.DentistServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/dentist")
public class DentistController {


    //@Autowired
    private final DentistServiceImp dentistService;
    public DentistController(DentistServiceImp dentistService)
    {this.dentistService=dentistService;}



    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getall")
    @ResponseBody
    public List<Dentist> getall(){
        System.out.println("pozvan je kontroler getall...");
        return dentistService.getAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Dentist>> getById(@PathVariable Long id){
        System.out.println("pozvan je kontroler getById...");
        return dentistService.getById(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/name/{fullName}")
    @ResponseBody
    public ResponseEntity<List<Dentist>> getByFullName(@PathVariable String fullName){
        System.out.println("pozvan je kontroler getByFullName...");
        return dentistService.getByFullName(fullName);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/contact/{contact}")
    @ResponseBody
    public ResponseEntity<Optional<Dentist>> getByContact(@PathVariable String contact){
        System.out.println("pozvN je kontroler getByContact...");
        return dentistService.getByContact(contact);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("")
    @ResponseBody
    public Dentist addNew(@RequestBody Dentist dentist){
        System.out.println("pozvan je kontroler addNew");
        return dentistService.addNew(dentist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> removeDentist(@PathVariable Long id){
        System.out.println("pozvan je kontroler removeDentist...");
        return dentistService.removeDentist(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseBody
    public Optional<Dentist> updateDentist(@PathVariable Long id, @RequestBody Dentist dentist){
        System.out.println("pozvan je kontroler updateDentist...");
        return dentistService.updateDentist(id,dentist);

    }



}
