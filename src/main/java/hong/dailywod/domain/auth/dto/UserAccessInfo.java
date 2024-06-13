package hong.dailywod.domain.auth.dto;

import hong.dailywod.domain.role.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UserAccessInfo {

    private Long id;
    private String email;
    private Role role;

    public UserAccessInfo(Claims claims) {
        this.id = Long.parseLong(claims.get("id", String.class));
        this.email = claims.get("email", String.class);
        this.role = Role.valueOf(claims.get("role", String.class));
    }
}
