/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vvmntl.fiters.JwtFilter;
import com.vvmntl.pojo.User;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.UserService;
import jakarta.ws.rs.HttpMethod;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author BRAVO15
 */
@Configuration
@EnableTransactionManagement
@EnableWebSecurity
@ComponentScan(basePackages = {
    "com.vvmntl.controllers",
    "com.vvmntl.services",
    "com.vvmntl.repositories",
    "com.vvmntl.validator",
    "com.vvmntl.formattor"
})
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private DoctorService doctorService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
    
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(userDetailService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable()).authorizeHttpRequests(requests
                -> requests.requestMatchers("/home", "/doctor", "/stats", "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/secure/workschedules/**").access(new DoctorAuthorizationManager())
                        .requestMatchers("/api/secure/patients/**").hasRole("PATIENT")
                        .requestMatchers("/api/secure/doctors/**").hasRole("DOCTOR")
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/specialize").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll()).addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000/")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "disqxvj3s",
                        "api_key", "591477333856363",
                        "api_secret", "R_tdHJCqiow28KiCrgzMQsRVe8c",
                        "secure", true));
        return cloudinary;
    }
    
    public class DoctorAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

        @Override
        public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) {
            Authentication authentication = authenticationSupplier.get();
            if (authentication == null || !authentication.isAuthenticated()) {
                return new AuthorizationDecision(false);
            }

            boolean hasDoctorRole = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DOCTOR"));
            if (!hasDoctorRole) {
                return new AuthorizationDecision(false);
            }

            String username = authentication.getName();
            User user = (User) userDetailService.loadUserByUsername(username);
            if (user == null) {
                return new AuthorizationDecision(false);
            }

            boolean isVerified = doctorService.isVerified(user.getId());
            return new AuthorizationDecision(isVerified);
        }
    }
    
}
