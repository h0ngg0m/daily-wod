package hong.dailywod.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Claims;

@SpringBootTest
class JwtProviderTest {

    @Autowired JwtProvider jwtProvider;

    @Test
    void 액세스_토큰을_생성할_수_있다() {
        // given
        Long id = 1L;
        String email = "hong@gmail.com";

        // when
        String token =
                jwtProvider.createAccessToken(Map.of("id", id.toString(), "email", email), 1000L);

        // then
        assertThat(token).isNotBlank();
    }

    @Test
    void 토큰에서_클레임을_얻을_수_있다() {
        // given
        Long id = 1L;
        String email = "hong@gmail.com";

        // when
        Claims claims =
                jwtProvider.getClaimsFrom(
                        jwtProvider.createAccessToken(
                                Map.of("id", id.toString(), "email", email), 1000L));

        // then
        assertThat(Long.parseLong(claims.get("id", String.class))).isEqualTo(id);
        assertThat(claims.get("email", String.class)).isEqualTo(email);
    }

    @Test
    void 유효한_토큰인지_확인할_수_있다() {
        // given
        Long id = 1L;
        String email = "hong@gmail.com";
        String accessToken =
                jwtProvider.createAccessToken(Map.of("id", id.toString(), "email", email), 1000L);

        // when
        boolean isValid = jwtProvider.isValidToken(accessToken);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void 만료된_토큰은_유효하지_않다() throws InterruptedException {
        // given
        Long id = 1L;
        String email = "hong@gmail.com";
        String accessToken =
                jwtProvider.createAccessToken(Map.of("id", id.toString(), "email", email), 1L);

        // when
        Thread.sleep(1);
        boolean isValid = jwtProvider.isValidToken(accessToken);

        // then
        assertThat(isValid).isFalse();
    }
}
