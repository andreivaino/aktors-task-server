package ee.aktors.andrei.task.controller;

import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.service.OrderService;
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
@RequestMapping("/orders")
public class OrderRestController {

    private OrderService orderService;

    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderNumber) throws CustomException {
        OrderDto orderDto = orderService.getOrderByOrderNumber(orderNumber);
        log.info("Retrieving order with order number " + orderNumber);
        return new ResponseEntity<>(orderDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        List<OrderDto> allOrders = orderService.getAllOrders();
        log.info("Retrieving all orders");
        return new ResponseEntity<>(allOrders, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Validated @RequestBody OrderDto orderDto) {
        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        log.info("Order with order number " + createdOrderDto.getOrderNumber() + " created");
        return new ResponseEntity<>(createdOrderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderNumber) throws CustomException {
        orderService.deleteOrderByOrderNumber(orderNumber);
        log.info("Order with order number " + orderNumber + " deleted");
        return new ResponseEntity<>("Order with order number " + orderNumber + " deleted", HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateOrder(@Validated @RequestBody OrderDto orderDto) throws CustomException {
        OrderDto updatedOrderDto = orderService.updateOrder(orderDto);
        log.info("Order with order number " + orderDto.getOrderNumber() + " updated");
        return new ResponseEntity<>(updatedOrderDto, HttpStatus.ACCEPTED);
    }

}
