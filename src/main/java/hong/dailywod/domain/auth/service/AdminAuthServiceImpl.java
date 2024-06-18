package hong.dailywod.domain.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hong.dailywod.domain.auth.apiclient.GetGoogleAccessTokenClient;
import hong.dailywod.domain.auth.apiclient.GetGoogleUserInfoClient;
import hong.dailywod.domain.auth.dto.*;
import hong.dailywod.domain.role.model.Role;
import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.user.repository.UserRepository;
import hong.dailywod.global.exception.ClientBadRequestException;
import hong.dailywod.global.exception.ExceptionCode;
import hong.dailywod.global.exception.SystemException;
import hong.dailywod.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final GetGoogleAccessTokenClient googleAuthApiClient;
    private final GetGoogleUserInfoClient googleUserInfoClient;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${auth.google.admin.client-id}")
    private String clientId;

    @Value("${auth.google.admin.client-secret}")
    private String clientSecret;

    @Value("${auth.google.admin.redirect-uri}")
    private String redirectUri;

    @Value("${auth.google.admin.grant-type}")
    private String grantType;

    @Value("${jwt.access-expiration}")
    private Long accessExpiration;

    private static final List<String> ADMIN_EMAILS =
            List.of("jaehongbyeon98@gmail.com"); // TODO: 코드에서 숨기기

    @Override
    public JwtResponseDto googleLogin(CodeDto dto) {
        ResponseEntity<String> accessTokenResponse =
                googleAuthApiClient.getAccessToken(
                        new GoogleRequestAccessTokenDto(
                                dto.getCode(), clientId, clientSecret, redirectUri, grantType));

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleResponseAccessTokenDto accessTokenDto;

        try {
            accessTokenDto =
                    objectMapper.readValue(
                            accessTokenResponse.getBody(), GoogleResponseAccessTokenDto.class);
            ResponseEntity<String> userInfoResponse =
                    googleUserInfoClient.getUserInfo(accessTokenDto.getAccessToken());
            GoogleResponseUserInfoDto userInfoDto =
                    objectMapper.readValue(
                            userInfoResponse.getBody(), GoogleResponseUserInfoDto.class);

            // TODO: 정상 동작하는지 확인 필요
            if (ADMIN_EMAILS.stream().noneMatch(userInfoDto.getEmail()::equals)) {
                throw new ClientBadRequestException(ExceptionCode.FAIL_UNAUTHORIZED);
            }

            User user =
                    userRepository
                            .findByEmail(userInfoDto.getEmail())
                            .orElseGet(
                                    () ->
                                            userRepository.persist(
                                                    new User(userInfoDto.getEmail(), Role.ADMIN)));
            return new JwtResponseDto(
                    jwtProvider.createAccessToken(
                            Map.of(
                                    "id",
                                    user.getId().toString(),
                                    "email",
                                    user.getEmail(),
                                    "role",
                                    user.getRole().toString()),
                            accessExpiration));
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
            throw new SystemException(ExceptionCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
