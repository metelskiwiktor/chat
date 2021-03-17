package pl.wsb.chat.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Profile("local")
public class CrossOriginConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${local.frontend.host}") final String localFrontendHost) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(localFrontendHost);
            }
        };
    }
}
