package ee.aktors.andrei.task.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotNull
    private Long personalId;
    @NotBlank
    @Size(min = 1, max = 15)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 20)
    private String lastName;
    @NotBlank
    private String phone;
    @NotBlank
    @Size(min = 3, max = 35)
    private String country;
    @NotBlank
    @Size(min = 1, max = 80)
    private String address;
    @JsonIgnoreProperties("client")
    private List<OrderDto> orders;

}
