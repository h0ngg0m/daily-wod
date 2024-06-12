package hong.dailywod.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.dailywod.domain.auth.dto.CodeDto;
import hong.dailywod.domain.auth.dto.JwtResponseDto;
import hong.dailywod.domain.auth.service.UserAuthService;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login/google")
    public ResponseEntity<ApiResult<JwtResponseDto>> userGoogleLogin(@RequestBody CodeDto dto) {
        return ResponseFactory.ok(userAuthService.googleLogin(dto));
    }
}
