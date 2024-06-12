package hong.dailywod.domain.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class GoogleRequestAccessTokenDto {

    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
}
