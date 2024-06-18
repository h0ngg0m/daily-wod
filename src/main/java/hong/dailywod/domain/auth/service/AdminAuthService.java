package hong.dailywod.domain.auth.service;

import hong.dailywod.domain.auth.dto.CodeDto;
import hong.dailywod.domain.auth.dto.JwtResponseDto;

public interface AdminAuthService {

    JwtResponseDto googleLogin(CodeDto dto);
}
