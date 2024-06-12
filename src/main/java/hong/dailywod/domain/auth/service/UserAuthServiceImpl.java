package hong.dailywod.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hong.dailywod.domain.auth.apiclient.GetGoogleAccessTokenClient;
import hong.dailywod.domain.auth.apiclient.GetGoogleUserInfoClient;
import hong.dailywod.domain.auth.dto.*;
import hong.dailywod.domain.auth.jwt.JwtProvider;
import hong.dailywod.domain.user.model.User;
import hong.dailywod.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final GetGoogleAccessTokenClient googleAuthApiClient;
    private final GetGoogleUserInfoClient googleUserInfoClient;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${auth.google.client-id}")
    private String clientId;

    @Value("${auth.google.client-secret}")
    private String clientSecret;

    @Value("${auth.google.redirect-uri}")
    private String redirectUri;

    @Value("${auth.google.grant-type}")
    private String grantType;

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

            User user =
                    userRepository
                            .findByEmail(userInfoDto.getEmail())
                            .orElseGet(
                                    () -> userRepository.persist(new User(userInfoDto.getEmail())));

            return new JwtResponseDto(jwtProvider.createAccessToken(user.getId(), user.getEmail()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: custom exception
        }
    }
}
