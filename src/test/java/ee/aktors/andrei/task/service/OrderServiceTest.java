package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.OrderRepository;
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
class OrderServiceTest {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    OrderServiceTest(
            OrderService orderService,
            OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(orderService);
    }


    @Order(2)
    @Test
    public void shouldThrowCustomExceptionIfOrderNumberDoesNotExist(){
        Long notExistedOrderNumber = 233L;
        OrderDto orderDto = OrderDto.builder()
                .orderNumber(notExistedOrderNumber)
                .build();
        assertNull(orderRepository.findById(notExistedOrderNumber).orElse(null));
        assertThrows(CustomException.class, () -> orderService.getOrderByOrderNumber(notExistedOrderNumber));
        assertThrows(CustomException.class, () -> orderService.deleteOrderByOrderNumber(notExistedOrderNumber));
        assertThrows(CustomException.class, () -> orderService.updateOrder(orderDto));
    }

    @Order(3)
    @Test
    public void shouldThrowCustomExceptionIfOrderNumberIsNull() {
        OrderDto orderDto = OrderDto.builder()
                .build();
        assertNull(orderDto.getOrderNumber());
        assertThrows(CustomException.class, () -> orderService.updateOrder(orderDto));
    }

}
