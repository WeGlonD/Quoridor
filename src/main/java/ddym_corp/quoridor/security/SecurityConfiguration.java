package ddym_corp.quoridor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.sessionManagement((sessionManagement)->{
            sessionManagement
                    .sessionFixation().changeSessionId()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .sessionRegistry(sessionRegistry());
                });

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
