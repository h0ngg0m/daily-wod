package hong.dailywod.domain.user.dto;

import hong.dailywod.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private Long id;
    private String email;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
