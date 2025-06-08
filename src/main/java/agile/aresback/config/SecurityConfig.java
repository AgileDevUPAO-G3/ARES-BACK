package agile.aresback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // activa CORS según tu WebConfig
                .csrf(csrf -> csrf.disable()) // para API REST es común desactivarlo
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll() // login público
                        .anyRequest().authenticated() // el resto requiere auth (si tuvieras)
                );

        return http.build();
    }
}
