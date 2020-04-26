package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.domain.Product;
import ee.aktors.andrei.task.dto.ProductDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.mapper.ProductMapper;
import ee.aktors.andrei.task.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getProductsWithoutOrder() {
        return productRepository.findAllByOrderIsNull().stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getProductsByOrderNumber(Long orderNumber) {
        return productRepository.findAll().stream()
                .filter(product -> product.getOrder() != null)
                .filter(product -> product.getOrder().getOrderNumber().equals(orderNumber))
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto getProductByBarcode(String barcode) throws CustomException {
        Product product = productRepository.findById(barcode).orElseThrow(() -> {
                log.warn("getProductById request with not existed barcode");
                return new CustomException("Product with barcode " + barcode + " not found.",
                        HttpStatus.NOT_FOUND);
        });
        return productMapper.map(product);
    }

    @Transactional
    public void deleteProductByBarcode(String barcode) throws CustomException {
        if (!productRepository.existsById(barcode)) {
            log.warn("deleteProductById request with not existed barcode");
            throw new CustomException("Product with barcode " + barcode + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(barcode);
    }

    @Transactional
    public ProductDto updateProduct(ProductDto productDto) throws CustomException {
        if (productDto.getBarcode() == null) {
            log.warn("updateProduct request with null barcode");
            throw new CustomException("Product barcode can't be null.",
                    HttpStatus.BAD_REQUEST);
        } else {
            Product tempProduct = productMapper.map(productDto);
            return productMapper.map(productRepository.save(tempProduct));
        }
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) throws CustomException {
        if (productRepository.existsById(productDto.getBarcode())) {
            log.warn("createProduct request with existed personalId");
            throw new CustomException("Product barcode is already in use.",
                    HttpStatus.BAD_REQUEST);
        }
        Product tempProduct = productMapper.map(productDto);
        Product product = productRepository.save(tempProduct);
        return productMapper.map(product);
    }

}
