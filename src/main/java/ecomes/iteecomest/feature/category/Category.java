package ecomes.iteecomest.feature.category;
import ecomes.iteecomest.feature.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    private String description;
    private String icon;
    Boolean isDeleted = false;
    @ManyToOne
    private Category parentCategory;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
