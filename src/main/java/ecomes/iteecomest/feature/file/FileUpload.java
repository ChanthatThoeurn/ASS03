package ecomes.iteecomest.feature.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    @Size(max = 15)
    private String extension;

    @Column(nullable = false, unique = true)
    private String name;

    private String caption;

    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String mediaType;

}
