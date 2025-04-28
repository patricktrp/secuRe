package de.rwth_aachen.swc.recsec.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration for the application.
 *
 * This configuration sets up security rules using Spring Security, including
 * JWT-based OAuth2 resource server setup and Cross-Origin Resource Sharing (CORS) policies.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * The URI of the JSON Web Key (JWK) set for validating JWT tokens.
     * Injected from the application properties.
     */
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    /**
     * Configures the SecurityFilterChain bean.
     *
     * @param http the HttpSecurity object used to configure security features
     * @return the configured SecurityFilterChain
     * @throws Exception if a security configuration error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configure CORS settings using the CORS configuration source
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                // Define authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to API documentation and Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        // Require authentication for all other requests
                        .anyRequest().authenticated()
                )
                // Set up OAuth2 resource server for JWT validation
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwkSetUri(jwkSetUri)));

        // Build and return the configured SecurityFilterChain
        return http.build();
    }

    /**
     * Configures the CORS settings for the application.
     *
     * @return a CorsConfigurationSource with the specified CORS rules
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Define allowed origins for cross-origin requests
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Define allowed HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        // Allow all headers in cross-origin requests
        configuration.setAllowedHeaders(List.of("*"));
        // Enable sending of credentials in CORS requests
        configuration.setAllowCredentials(true);

        // Register the CORS configuration for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}