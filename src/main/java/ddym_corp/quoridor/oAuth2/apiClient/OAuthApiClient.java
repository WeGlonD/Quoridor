package ddym_corp.quoridor.oAuth2.apiClient;

import ddym_corp.quoridor.oAuth2.infoResponse.OAuthInfoResponse;
import ddym_corp.quoridor.oAuth2.callbackParams.OAuthCallbackParams;
import ddym_corp.quoridor.oAuth2.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthCallbackParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}