package ecomes.iteecomest.feature.oder;
import ecomes.iteecomest.feature.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Oder order;
    @ManyToOne
    private Product product;
    private Integer qty;
    private BigDecimal UnitPrice;

}
