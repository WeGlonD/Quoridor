package ddym_corp.quoridor.oAuth2.infoResponse;

import ddym_corp.quoridor.oAuth2.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}