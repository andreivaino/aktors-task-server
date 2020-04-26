package ee.aktors.andrei.task.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "order")
@ToString(exclude = {"order"})
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @Column(name = "barcode")
    private String barcode;

    @Column(name = "name")
    private String name;

    @Type(type = "big_decimal")
    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "release_date")
    private Date releaseDate;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_number")
    private Order order;

}
