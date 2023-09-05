package ddym_corp.quoridor.user.auth.oAuth2.callbackParams;

import ddym_corp.quoridor.user.auth.oAuth2.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthCallbackParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
