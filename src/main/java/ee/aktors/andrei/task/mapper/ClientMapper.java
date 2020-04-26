package ee.aktors.andrei.task.mapper;

import ee.aktors.andrei.task.domain.Client;
import ee.aktors.andrei.task.domain.Order;
import ee.aktors.andrei.task.domain.Product;
import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.dto.ProductDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    ClientDto map(Client client);
    Client map(ClientDto clientDto, @MappingTarget Client client);
    Client map(ClientDto clientDto);

    @Mapping(target = "client", ignore = true)
    OrderDto mapWithoutClient(Order order);
    @Mapping(target = "client", ignore = true)
    Order map(OrderDto order);
    @Mapping(target = "order", ignore = true)
    Product map(ProductDto productDto);
    @Mapping(target = "order", ignore = true)
    ProductDto map(Product productDto);

    List<OrderDto> mapOrdersToDTOs(List<Order> orders);
    List<Order> mapDTOsToOrders(List<OrderDto> orders);

}
