package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.ClientRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ClientServiceTest {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @Autowired
    ClientServiceTest(
            ClientService clientService,
            ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(clientService);
    }


    @Order(2)
    @Test
    public void shouldThrowCustomExceptionIfPersonalIdDoesNotExist(){
        Long notExistedPersonalId = 9999999999L;
        ClientDto clientDto = ClientDto.builder()
                .personalId(notExistedPersonalId)
                .build();
        assertNull(clientRepository.findByPersonalId(notExistedPersonalId).orElse(null));
        assertThrows(CustomException.class, () -> clientService.getClientByPersonalId(notExistedPersonalId));
        assertThrows(CustomException.class, () -> clientService.deleteClientByPersonalId(notExistedPersonalId));
        assertThrows(CustomException.class, () -> clientService.updateClient(clientDto));

    }

    @Order(3)
    @Test
    public void shouldThrowCustomExceptionIfPersonalIdExistsWhileCreating() throws CustomException {
        ClientDto clientByPersonalId = clientService.getClientByPersonalId(3880892256L);
        assertNotNull(clientByPersonalId);
        ClientDto clientDto = ClientDto.builder()
                .personalId(clientByPersonalId.getPersonalId())
                .build();
        assertThrows(CustomException.class, () -> clientService.createClient(clientDto));
    }

    @Order(4)
    @Test
    public void shouldThrowCustomExceptionIfPersonalIdIsNull() {
        ClientDto clientDto = ClientDto.builder()
                .build();
        assertNull(clientDto.getPersonalId());
        assertThrows(CustomException.class, () -> clientService.updateClient(clientDto));
    }

}
