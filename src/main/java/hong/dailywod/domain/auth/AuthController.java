package hong.dailywod.domain.auth;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.dailywod.domain.auth.dto.CodeDto;
import hong.dailywod.domain.auth.dto.JwtResponseDto;
import hong.dailywod.domain.auth.dto.UserAccessInfo;
import hong.dailywod.domain.auth.service.UserAuthService;
import hong.dailywod.domain.auth.util.AccessInfoUtil;
import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;
    private final AccessInfoUtil accessInfoUtil;

    @PostMapping("/api/v1/auth/login/google")
    public ResponseEntity<ApiResult<JwtResponseDto>> userGoogleLogin(@RequestBody CodeDto dto) {
        return ResponseFactory.ok(userAuthService.googleLogin(dto));
    }

    @GetMapping("/api/v1/test")
    public ResponseEntity<ApiResult<UserAccessInfo>> test(HttpServletRequest request) {
        return ResponseFactory.ok(accessInfoUtil.getUserAccessInfo(request));
    }

    //    @PostMapping("/admin-api/v1/auth/login/google")
    //    public ResponseEntity<ApiResult<JwtResponseDto>> adminGoogleLogin(@RequestBody CodeDto
    // dto) {
    //        System.out.println("AuthController.adminGoogleLogin");
    //        return null;
    //    }
}
