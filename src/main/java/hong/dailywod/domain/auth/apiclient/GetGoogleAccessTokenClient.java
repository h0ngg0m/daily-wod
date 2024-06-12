package hong.dailywod.domain.auth.apiclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import hong.dailywod.domain.auth.dto.GoogleRequestAccessTokenDto;

@FeignClient(name = "getGoogleAccessTokenClient", url = "https://oauth2.googleapis.com/")
public interface GetGoogleAccessTokenClient {

    @PostMapping("/token")
    ResponseEntity<String> getAccessToken(@RequestBody GoogleRequestAccessTokenDto dto);
}
