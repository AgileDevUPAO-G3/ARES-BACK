package agile.aresback.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import agile.aresback.model.entity.User;
import agile.aresback.repository.UserRepository;
import agile.aresback.config.CustomUserDetails;
import agile.aresback.exception.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // activa CORS según tu WebConfig
                .csrf(csrf -> csrf.disable()) // para API REST es común desactivarlo
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/disponibilidad/**").permitAll()
                        .requestMatchers("/api/clients/byDni/**").permitAll()
                        .requestMatchers("/api/reservations/**").permitAll() // POST crear reserva
                        .requestMatchers("/api/mercado-pago/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/tables/**").permitAll()


                        // Endpoints privados (requieren login)
                        .requestMatchers("/api/clients/**").authenticated()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/zones/**").authenticated()
                        .requestMatchers("/api/tables/**").authenticated()

                        // Cualquier otro endpoint → requiere autenticación
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
