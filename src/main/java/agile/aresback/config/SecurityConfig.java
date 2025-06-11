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
                .csrf(csrf -> csrf.disable())    // para API REST es común desactivarlo
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/disponibilidad").permitAll()
                        .requestMatchers("/api/disponibilidad/**").permitAll()
                        .requestMatchers("/api/clients/byDni/**").permitAll()
                        .requestMatchers("/api/reservations").permitAll() // POST crear reserva
                        .requestMatchers("/api/mercado-pago/**").permitAll()

                        // Endpoints privados (requieren login)
                        .requestMatchers("/api/clients/**").authenticated()
                        .requestMatchers("/api/reservations/**").authenticated() // el resto de reservations
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/zones/**").authenticated()
                        .requestMatchers("/api/tables/**").authenticated()

                        // Cualquier otro endpoint → requiere autenticación
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
