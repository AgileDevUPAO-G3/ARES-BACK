package agile.aresback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // permite todas las rutas
                        //.allowedOrigins("http://localhost:4200") // permite Angular
                        .allowedOrigins("https://restaurante-pacha.netlify.app/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ajusta según tus endpoints
                        .allowedHeaders("*");
            }
        };
    }
}