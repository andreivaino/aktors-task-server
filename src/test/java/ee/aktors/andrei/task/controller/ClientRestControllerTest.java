package ee.aktors.andrei.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.ClientRepository;
import ee.aktors.andrei.task.service.ClientService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ClientRestControllerTest {

    @Autowired
    public ClientRestControllerTest(
            ClientRestController clientRestController,
            ClientRepository clientRepository,
            ClientService clientService,
            MockMvc mockMvc) {
        this.clientRestController = clientRestController;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.mockMvc = mockMvc;
    }

    private final ClientRestController clientRestController;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final MockMvc mockMvc;

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(clientRestController);
    }

    @Order(2)
    @Test
    public void shouldReturnClientJson() throws Exception {
        this.mockMvc.perform(get("/clients/3880892256")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'firstName': 'Andrei'}"));
    }

    @Order(3)
    @Test
    public void shouldReturnClientsJsonList() throws Exception {
        this.mockMvc.perform(get("/clients")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].firstName").value("Andrei"))
                .andExpect(jsonPath("$[1].lastName").value("Oneil"));
    }

    @Order(4)
    @Test
    public void shouldCreateClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientDto clientDto = new ClientDto(
                12343L,
                "Tom",
                "Jonson",
                "+3725654",
                "Estonia",
                "Tallinn, Nurmenuku 23-23",
                null);
        this.mockMvc.perform(post("/clients")
                .content(objectMapper.writeValueAsString(clientDto))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(clientDto.getFirstName()));
        assertNotNull(this.clientService.getClientByPersonalId(clientDto.getPersonalId()));
    }

    @Order(5)
    @Test
    public void shouldUpdateClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientDto clientDto = this.clientService.getClientByPersonalId(3880892299L);
        clientDto.setOrders(null);
        assertEquals("Lee", clientDto.getFirstName());
        clientDto.setFirstName("Oleg");
        this.mockMvc.perform(put("/clients")
                .content(objectMapper.writeValueAsString(clientDto))
                .contentType("application/json"))
                .andExpect(status().isOk());
        assertEquals("Oleg", this.clientService.getClientByPersonalId(clientDto.getPersonalId()).getFirstName());
    }

    @Order(6)
    @Test
    public void shouldDeleteClient() throws Exception {
        Long personalId = 3880892299L;
        assertNotNull(this.clientRepository.findByPersonalId(personalId).orElse(null));
        this.mockMvc.perform(delete("/clients/" + personalId)
                .contentType("application/json"))
                .andExpect(status().isAccepted());
        assertNull(this.clientRepository.findByPersonalId(personalId).orElse(null));
        assertThrows(CustomException.class, () -> this.clientService.getClientByPersonalId(personalId));
    }

}
