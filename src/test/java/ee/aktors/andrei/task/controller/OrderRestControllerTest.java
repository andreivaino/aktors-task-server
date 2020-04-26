package ee.aktors.andrei.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.OrderRepository;
import ee.aktors.andrei.task.service.ClientService;
import ee.aktors.andrei.task.service.OrderService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest()
@ActiveProfiles("test")
@AutoConfigureMockMvc
class OrderRestControllerTest {

    private final OrderRestController orderRestController;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ClientService clientService;
    private final MockMvc mockMvc;

    @Autowired
    public OrderRestControllerTest(
            OrderRestController orderRestController,
            OrderRepository orderRepository,
            OrderService orderService,
            ClientService clientService,
            MockMvc mockMvc
    ) {
        this.orderRestController = orderRestController;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.clientService = clientService;
        this.mockMvc = mockMvc;
    }

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(orderRestController);
    }

    @Order(2)
    @Test
    public void shouldReturnOrder() throws Exception {
        this.mockMvc.perform(get("/orders/1")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{'price': 536.25}"));
    }

    @Order(3)
    @Test
    public void shouldReturnOrdersList() throws Exception {
        this.mockMvc.perform(get("/orders")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].client.personalId").value(3880892256L))
                .andExpect(jsonPath("$[1].price").value(1497.95));
    }

    @Order(4)
    @Test
    public void shouldCreateOrder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientDto clientDto = clientService.getClientByPersonalId(3880892256L);
        OrderDto orderDto = OrderDto.builder()
                .price(new BigDecimal("123"))
                .transactionDate(new Date())
                .client(clientDto)
                .build();
        this.mockMvc.perform(post("/orders")
                .content(objectMapper.writeValueAsString(orderDto))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(123));
        assertNotNull(this.orderService.getOrderByOrderNumber(5L));
    }

    @Order(5)
    @Test
    public void shouldUpdateOrder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDto orderDto = this.orderService.getOrderByOrderNumber(3L);
        assertEquals(orderDto.getPrice(), new BigDecimal("228196.25"));
        orderDto.setPrice(new BigDecimal("22"));
        this.mockMvc.perform(put("/orders")
                .content(objectMapper.writeValueAsString(orderDto))
                .contentType("application/json"))
                .andExpect(status().isAccepted());
        assertEquals(new BigDecimal("22.00"), this.orderService.getOrderByOrderNumber(orderDto.getOrderNumber()).getPrice());
    }

    @Order(6)
    @Test
    public void shouldDeleteOrder() throws Exception {
        Long orderNumber = 1L;
        assertNotNull(this.orderRepository.findById(orderNumber).orElse(null));
        this.mockMvc.perform(delete("/orders/" + orderNumber)
                .contentType("application/json"))
                .andExpect(status().isOk());
        assertNull(this.orderRepository.findById(orderNumber).orElse(null));
        assertThrows(CustomException.class, () -> this.orderService.getOrderByOrderNumber(orderNumber));
    }

}
