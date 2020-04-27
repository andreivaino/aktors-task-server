package ee.aktors.andrei.task.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"products", "client"})
@ToString(exclude = {"products", "client"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Long orderNumber;

    @NotNull
    @Type(type = "big_decimal")
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @CreatedDate
    @Column(name = "transaction_date")
    private Date transactionDate;

    @NotNull
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_personal_id")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<Product> products = new ArrayList<>();

}
