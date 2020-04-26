package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.dto.ProductDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.repository.ProductRepository;
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
class ProductServiceTest {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    ProductServiceTest(
            ProductService productService,
            ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Order(1)
    @Test
    public void contextLoads() {
        assertNotNull(productService);
    }


    @Order(2)
    @Test
    public void shouldThrowCustomExceptionIfBarcodeDoesNotExist(){
        String notExistedBarcode = "1111111111111";
        ProductDto productDto = ProductDto.builder()
                .barcode(notExistedBarcode)
                .build();
        assertNull(productRepository.findById(notExistedBarcode).orElse(null));
        assertThrows(CustomException.class, () -> productService.getProductByBarcode(notExistedBarcode));
        assertThrows(CustomException.class, () -> productService.deleteProductByBarcode(notExistedBarcode));
    }

    @Order(3)
    @Test
    public void shouldThrowCustomExceptionIfBarcodeExistsWhileCreating() throws CustomException {
        ProductDto productByBarcode = productService.getProductByBarcode("3675112344452");
        assertNotNull(productByBarcode);
        ProductDto productDto = ProductDto.builder()
                .barcode(productByBarcode.getBarcode())
                .build();
        assertThrows(CustomException.class, () -> productService.createProduct(productDto));
    }

    @Order(4)
    @Test
    public void shouldThrowCustomExceptionIfBarcodeIsNull() {
        ProductDto productDto = ProductDto.builder()
                .build();
        assertNull(productDto.getBarcode());
        assertThrows(CustomException.class, () -> productService.updateProduct(productDto));
    }

}
