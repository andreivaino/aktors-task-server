package ee.aktors.andrei.task.controller;

import ee.aktors.andrei.task.dto.ProductDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.service.ProductService;
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
@RequestMapping("/products")
public class ProductRestController {

    private ProductService productService;

    @GetMapping("/{barcode}")
    public ResponseEntity<?> getProductById(@PathVariable String barcode) throws CustomException {
        ProductDto productDto = productService.getProductByBarcode(barcode);
        log.info("Retrieving product with personal barcode " + barcode);
        return new ResponseEntity<>(productDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> allProducts = productService.getAllProducts();
        log.info("Retrieving all products");
        return new ResponseEntity<>(allProducts, HttpStatus.ACCEPTED);
    }

    @GetMapping("/no-order")
    public ResponseEntity<?> getProductsWithoutOrder() {
        List<ProductDto> allProducts = productService.getProductsWithoutOrder();
        log.info("Retrieving all products without order");
        return new ResponseEntity<>(allProducts, HttpStatus.ACCEPTED);
    }

    @GetMapping("/by-order/{orderNumber}")
    public ResponseEntity<?> getProductsByOrderNumber(@PathVariable Long orderNumber) {
        List<ProductDto> allProducts = productService.getProductsByOrderNumber(orderNumber);
        log.info("Retrieving products with order number " + orderNumber);
        return new ResponseEntity<>(allProducts, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductDto productDto) throws CustomException {
        ProductDto createdProductDto = productService.createProduct(productDto);
        log.info("Product with barcode " + createdProductDto.getBarcode() + " created");
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{barcode}")
    public ResponseEntity<?> deleteProduct(@PathVariable String barcode) throws CustomException {
        productService.deleteProductByBarcode(barcode);
        log.info("Product with barcode " + barcode + " deleted");
        return new ResponseEntity<>("Product with barcode " + barcode + " deleted", HttpStatus.ACCEPTED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProduct(@Validated @RequestBody ProductDto productDto) throws CustomException {
        ProductDto updatedProductDto = productService.updateProduct(productDto);
        log.info("Product with personal barcode " + productDto.getBarcode() + " updated");
        return new ResponseEntity<>(updatedProductDto, HttpStatus.ACCEPTED);
    }

}
