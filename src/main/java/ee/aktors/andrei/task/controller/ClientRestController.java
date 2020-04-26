package ee.aktors.andrei.task.controller;

import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clients")
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping("/{personalId}")
    public ResponseEntity<?> getClientByPersonalId(@Validated @PathVariable Long personalId) throws CustomException {
        ClientDto clientDto = clientService.getClientByPersonalId(personalId);
        log.info("Retrieving client with personal id " + personalId);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllClients() {
        List<ClientDto> allClients = clientService.getAllClients();
        log.info("Retrieving all clients");
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createClient(@Validated @RequestBody ClientDto clientDto) throws CustomException {
        ClientDto createdClientDto = clientService.createClient(clientDto);
        log.info("Client with personal id " + createdClientDto.getPersonalId() + " created");
        return new ResponseEntity<>(createdClientDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{personalId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long personalId) throws CustomException {
        clientService.deleteClientByPersonalId(personalId);
        log.info("Client with personal id " + personalId + " deleted");
        return new ResponseEntity<>("Client with personal id " + personalId + " deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateClient(@Validated @RequestBody ClientDto clientDto) throws CustomException {
        ClientDto updatedClientDto = clientService.updateClient(clientDto);
        log.info("Client with personal id " + clientDto.getPersonalId() + " updated");
        return new ResponseEntity<>(updatedClientDto, HttpStatus.OK);
    }

}
