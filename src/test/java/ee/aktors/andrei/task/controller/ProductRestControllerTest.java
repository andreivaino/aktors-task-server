package ee.aktors.andrei.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.dto.ProductDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.ProductRepository;
import ee.aktors.andrei.task.service.OrderService;
import ee.aktors.andrei.task.service.ProductService;
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
class ProductRestControllerTest {

    private final ProductRestController productRestController;
    private final ProductService productService;
    private final MockMvc mockMvc;
    private final OrderService orderService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductRestControllerTest(
            ProductRestController productRestController,
            ProductService productService,
            OrderService orderService,
            MockMvc mockMvc,
            ProductRepository productRepository) {
        this.productRestController = productRestController;
        this.productService = productService;
        this.orderService = orderService;
        this.mockMvc = mockMvc;
        this.productRepository = productRepository;
    }

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(productRestController);
    }

    @Order(2)
    @Test
    public void shouldReturnProduct() throws Exception {
        this.mockMvc.perform(get("/products/1234449012643")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{'name': 'Lamborgini'}"));
    }

    @Order(3)
    @Test
    public void shouldReturnProductsList() throws Exception {
        this.mockMvc.perform(get("/products")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", hasSize(20)))
                .andExpect(jsonPath("$[0].order.price").value(536.25))
                .andExpect(jsonPath("$[1].description").value("Info about laptop."));
    }

    @Order(4)
    @Test
    public void shouldReturnProductsWithoutOrderList() throws Exception {
        this.mockMvc.perform(get("/products/no-order")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect((jsonPath("$[0].order")).doesNotExist())
                .andExpect((jsonPath("$[1].order")).doesNotExist())
                .andExpect((jsonPath("$[2].order")).doesNotExist())
                .andExpect((jsonPath("$[3].order")).doesNotExist());
    }

    @Order(5)
    @Test
    public void shouldReturnProductsByOrderNumber() throws Exception {
        Long orderNumber = 1L;
        this.mockMvc.perform(get("/products/by-order/1")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect((jsonPath("$[0].order.orderNumber")).value(orderNumber))
                .andExpect((jsonPath("$[1].order.orderNumber")).value(orderNumber))
                .andExpect((jsonPath("$[2].order.orderNumber")).value(orderNumber))
                .andExpect((jsonPath("$[3].order.orderNumber")).value(orderNumber));
    }

    @Order(6)
    @Test
    public void shouldCreateProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = ProductDto.builder()
                .barcode("1234339012643")
                .name("Test product")
                .basePrice(new BigDecimal("1234"))
                .description("Test description")
                .releaseDate(new Date())
                .build();
        this.mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test description"));
        assertNotNull(this.productService.getProductByBarcode("1234339012643"));
    }

    @Order(7)
    @Test
    public void shouldUpdateProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = this.productService.getProductByBarcode("1643675184362");
        OrderDto orderDto = this.orderService.getOrderByOrderNumber(1L);
        assertEquals("Phone", productDto.getName());
        productDto.setOrder(orderDto);
        this.mockMvc.perform(put("/products")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType("application/json"))
                .andExpect(status().isAccepted());
        assertEquals(this.productService.getProductByBarcode(productDto.getBarcode()).getOrder().getOrderNumber(), orderDto.getOrderNumber());
    }

    @Order(8)
    @Test
    public void shouldDeleteProduct() throws Exception {
        String barcode = "1264314377491";
        assertNotNull(this.productRepository.findById(barcode).orElse(null));
        this.mockMvc.perform(delete("/products/" + barcode)
                .contentType("application/json"))
                .andExpect(status().isAccepted());
        assertNull(this.productRepository.findById(barcode).orElse(null));
        assertThrows(CustomException.class, () -> this.productService.getProductByBarcode(barcode));
    }

}
