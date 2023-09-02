package ddym_corp.quoridor.oAuth2.callbackParams;

import ddym_corp.quoridor.oAuth2.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthCallbackParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
