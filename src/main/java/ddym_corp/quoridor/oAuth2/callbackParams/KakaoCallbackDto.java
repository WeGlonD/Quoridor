package ddym_corp.quoridor.oAuth2.callbackParams;

import ddym_corp.quoridor.oAuth2.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor
@Setter @Getter
public class KakaoCallbackDto implements OAuthCallbackParams {

    private String code;
    private String error;
    private String error_description;
    private String state;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        return body;
    }
}
