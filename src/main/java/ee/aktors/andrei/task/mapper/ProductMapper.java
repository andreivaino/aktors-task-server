package ee.aktors.andrei.task.mapper;

import ee.aktors.andrei.task.domain.Client;
import ee.aktors.andrei.task.domain.Order;
import ee.aktors.andrei.task.domain.Product;
import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductDto map(Product product);
    Product map(ProductDto productDto);

    @Mapping(target = "products", ignore = true)
    OrderDto map(Order order);
    @Mapping(target = "products", ignore = true)
    Order map(OrderDto orderDto);

    @Mapping(target = "orders", ignore = true)
    ClientDto map(Client client);
    @Mapping(target = "orders", ignore = true)
    Client map(ClientDto clientDto);

}
