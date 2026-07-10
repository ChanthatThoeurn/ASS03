package ecomes.iteecomest.feature.userProfile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "userProfiles")
public class UserProfile {
    @Id
    private String userId; // From Keycloak
    private String gender;
    private String biography;
    private String email;
    private String profilePicture;

}
