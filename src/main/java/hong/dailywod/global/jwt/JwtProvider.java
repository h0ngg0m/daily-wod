package hong.dailywod.global.jwt;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import hong.dailywod.global.exception.ClientBadRequestException;
import hong.dailywod.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    @Value("${jwt.issuer}")
    private String issuer;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public String createAccessToken(Map<String, String> claims, Long accessExpiration) {
        return Jwts.builder()
                .claims(claims)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Claims getClaimsFrom(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) { // TODO: 익셉션 세분화
            throw new ClientBadRequestException(ExceptionCode.FAIL_FORBIDDEN, "토큰이 유효하지 않습니다.");
        }
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) { // TODO: 익셉션 세분화
            return false;
        }
    }
}
