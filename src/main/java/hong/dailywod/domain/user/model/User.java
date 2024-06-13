package hong.dailywod.domain.user.model;

import jakarta.persistence.*;

import hong.dailywod.domain.role.model.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }
}
