package hong.dailywod.domain.auth.util;

import hong.dailywod.domain.wod.service.WodService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import hong.dailywod.domain.auth.dto.UserAccessInfo;
import hong.dailywod.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessInfoUtil {

    private final JwtProvider jwtProvider;

    public UserAccessInfo getUserAccessInfo(HttpServletRequest request) {
        return new UserAccessInfo(jwtProvider.getClaimsFrom(request.getHeader("Authorization")));
    }
}
