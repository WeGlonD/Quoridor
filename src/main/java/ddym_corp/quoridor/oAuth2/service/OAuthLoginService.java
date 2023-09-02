package ddym_corp.quoridor.oAuth2.service;

import ddym_corp.quoridor.oAuth2.OAuthProvider;
import ddym_corp.quoridor.oAuth2.apiClient.OAuthApiClient;
import ddym_corp.quoridor.oAuth2.infoResponse.OAuthInfoResponse;
import ddym_corp.quoridor.oAuth2.callbackParams.OAuthCallbackParams;
import ddym_corp.quoridor.user.User;
import ddym_corp.quoridor.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final Map<OAuthProvider, OAuthApiClient> clients;

    /**
     * Constructor
     * @param clients
     * @param userRepository
     */
    public OAuthLoginService(List<OAuthApiClient> clients, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    /**
     * @param params
     * @return email이 일치하는 user
     */
    public User login(OAuthCallbackParams params) {
        OAuthInfoResponse oAuthInfoResponse = request(params);
        Optional<User> optionalUser = userRepository.findByEmail(oAuthInfoResponse.getEmail());
        if(optionalUser.isEmpty()){
            return null;
        }
        return optionalUser.get();
    }

    /**
     * @param params
     * @return email,nickname, etc... 정보들
     */
    public OAuthInfoResponse request(OAuthCallbackParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}