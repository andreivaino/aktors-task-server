package ee.aktors.andrei.task.mapper;

import ee.aktors.andrei.task.domain.Order;
import ee.aktors.andrei.task.domain.Product;
import ee.aktors.andrei.task.dto.OrderDto;
import ee.aktors.andrei.task.dto.ProductDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "client.orders", ignore = true)
    OrderDto map(Order order);
    Order map(OrderDto orderDto, @MappingTarget Order order);
    Order map(OrderDto orderDto);

    @Mapping(target = "order", ignore = true)
    ProductDto map(Product product);
    @Mapping(target = "order", ignore = true)
    Product map(ProductDto productDto);

    List<ProductDto> mapProductsToDTOs(List<Product> products);
    List<Product> mapDTOsToProducts(List<ProductDto> products);


}
