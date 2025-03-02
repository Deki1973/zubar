package org.smg.springsecurity.controller;

import org.apache.coyote.Response;
import org.smg.springsecurity.dto.Response1;
import org.smg.springsecurity.model.Client;
import org.smg.springsecurity.service.serviceImp.ClientServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin    // js aplikacija nece da prihvati ovu anotaciju
@RestController
@RequestMapping("/client")
public class ClientController {

/*
    @Autowired
    private ClientRepository clientRepository;
*/

    // da probamo preko service layera
    //@Autowired
    private final ClientServiceImp clientService;
    public ClientController(ClientServiceImp clientService)
    {this.clientService=clientService;}


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseBody
    @GetMapping("/getall")
    public List<Client> getAll() {
        return clientService.findAll();
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseBody
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/contact/{contact}")
    public ResponseEntity<Client> getClientByContact(@PathVariable String contact) {
        return clientService.findClientByContact(contact);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ResponseBody
    @GetMapping("/name/{fullName}")
    public ResponseEntity<List<Client>> getClientByFullName(@PathVariable String fullName) {
        return clientService.findClientByFullName(fullName);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getallordbynameasc")
    public ResponseEntity<List<Client>> getAllOrdByNameAsc() {
        return clientService.findAllByOrderByFullNameAsc();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        String fullName = client.getFullName();
        String contact = client.getContact();
        String note = client.getNote();

        Response1 re1=clientService.updateClient(id, fullName, contact, note);
        System.out.println("rows affected: " + re1.getRetCode());
        if(re1.getRetCode()==200){
            return new ResponseEntity<>(client,HttpStatusCode.valueOf(200));
        }
        else{
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        System.out.println("pozvan kontroler addClient");

        return clientService.addClient(client);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeClient(@PathVariable Long id) {

        System.out.println("pozvan kontroler removeClient...");

        return clientService.removeClientById(id);

    }


}
