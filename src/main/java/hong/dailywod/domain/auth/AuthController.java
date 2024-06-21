package hong.dailywod.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.dailywod.domain.auth.dto.CodeDto;
import hong.dailywod.domain.auth.dto.JwtResponseDto;
import hong.dailywod.domain.auth.service.AdminAuthService;
import hong.dailywod.domain.auth.service.UserAuthService;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;
    private final AdminAuthService adminAuthService;

    @PostMapping("/api/v1/auth/login/google")
    public ResponseEntity<ApiResult<JwtResponseDto>> userGoogleLogin(@RequestBody CodeDto dto) {
        return ResponseFactory.ok(userAuthService.googleLogin(dto));
    }

    @PostMapping("/admin-api/v1/auth/login/google")
    public ResponseEntity<ApiResult<JwtResponseDto>> adminGoogleLogin(@RequestBody CodeDto dto) {
        return ResponseFactory.ok(adminAuthService.googleLogin(dto));
    }
}
