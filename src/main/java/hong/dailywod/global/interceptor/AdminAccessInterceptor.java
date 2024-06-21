package hong.dailywod.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import hong.dailywod.domain.role.model.Role;
import hong.dailywod.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminAccessInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        } else if (!jwtProvider.isValidToken(accessToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        Claims claims = jwtProvider.getClaimsFrom(accessToken);
        Role role = Role.valueOf(claims.get("role", String.class));
        if (!role.equals(Role.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
