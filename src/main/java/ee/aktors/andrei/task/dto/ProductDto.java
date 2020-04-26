package ee.aktors.andrei.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.aktors.andrei.task.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank
    @Size(min = 12, max = 13)
    private String barcode;
    @NotBlank
    @Size(min = 1, max = 40)
    private String name;
    @NotNull
    private BigDecimal basePrice;
    @NotNull
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @JsonIgnoreProperties("products")
    private OrderDto order;

}
