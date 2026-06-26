package ecomes.iteecomest.feature.oder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = false)
    private Float discount;
    private String remark;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private boolean isDeleted = false;

    @OneToMany(mappedBy = "order" ,  cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines;
}
