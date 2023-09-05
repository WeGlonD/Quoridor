package ddym_corp.quoridor.user.auth.oAuth2.apiClient;

import ddym_corp.quoridor.user.auth.oAuth2.apiClient.tockens.KakaoTokens;
import ddym_corp.quoridor.user.auth.oAuth2.callbackParams.OAuthCallbackParams;
import ddym_corp.quoridor.user.auth.oAuth2.infoResponse.KakaoInfoResponse;
import ddym_corp.quoridor.user.auth.oAuth2.infoResponse.OAuthInfoResponse;
import ddym_corp.quoridor.user.auth.oAuth2.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    /**
     * 서버로 부터 토큰을 받아오는 메소드
     * @param params
     * @return
     */
    @Override
    public String requestAccessToken(OAuthCallbackParams params) {
        String url = authUrl + "/oauth/token";
        log.info("url : {}", url);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);
        log.info("response : {}",response);

        assert response != null;
        return response.getAccessToken();
    }

    /**
     * 받은 토큰을 이용해 api에 접근하는 메소드
     * @param accessToken
     * @return
     */
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}