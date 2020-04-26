package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.domain.Client;
import ee.aktors.andrei.task.domain.Order;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.mapper.ClientMapper;
import ee.aktors.andrei.task.mapper.OrderMapper;
import ee.aktors.andrei.task.repository.ClientRepository;
import ee.aktors.andrei.task.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;
    private final ClientMapper clientMapper;

    @Transactional
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderByOrderNumber(Long orderNumber) throws CustomException {
        Optional<Order> orderOpt = orderRepository.findById(orderNumber);
        Order order = orderOpt.orElseThrow(() -> {
            log.warn("getOrderByOrderNumber request with not existed orderNumber");
            return new CustomException("Order with number " + orderNumber + " not found.",
                    HttpStatus.NOT_FOUND);
        });
        return orderMapper.map(order);
    }

    @Transactional
    public void deleteOrderByOrderNumber(Long orderNumber) throws CustomException {
        if (!orderRepository.existsById(orderNumber)) {
            log.warn("deleteOrderByOrderNumber request with not existed orderNumber");
            throw new CustomException("Order with number " + orderNumber + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        orderRepository.deleteById(orderNumber);
    }

    public OrderDto updateOrder(OrderDto orderDto) throws CustomException {
        if (orderDto.getOrderNumber() == null) {
            log.warn("updateOrder request with null orderNumber");
            throw new CustomException("Order number can't be null.",
                    HttpStatus.BAD_REQUEST);
        } else if (!orderRepository.existsById(orderDto.getOrderNumber())) {
            log.warn("updateOrder request with not existed orderNumber");
            throw new CustomException("Order with number " + orderDto.getOrderNumber() + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        return orderMapper.map(orderRepository.save(orderMapper.map(orderDto)));
    }


    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Client client = clientRepository.findByPersonalId(orderDto.getClient().getPersonalId()).get();
        orderDto.setClient(clientMapper.map(client));
        Order order = orderRepository.save(orderMapper.map(orderDto));
        return orderMapper.map(order);
    }

}
