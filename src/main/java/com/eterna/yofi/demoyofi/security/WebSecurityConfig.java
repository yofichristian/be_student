package com.eterna.yofi.demoyofi.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.url}")
    private String dbHost;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[- RESOURCE SERVER -] : [ ---------------------------- ] : ********** ");
        log.info("[- RESOURCE SERVER -] : [ - SERVER START SUCCESSFULLY- ] : ********** ");
        log.info("[- RESOURCE SERVER -] : [ ---------------------------- ] : ********** ");
        log.info("[- RESOURCE SERVER -] : PGSQL Host : {}", dbHost);
        log.info("[- RESOURCE SERVER -] : DB User    : {}", dbUser);
    }

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //        log.debug("security enabled = {}", securityEnabled);
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()) // Custom authentication provider
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .httpBasic(Customizer.withDefaults())   //must exist with Basic Authentication
                .userDetailsService(userDetailsService) //must exist
                .authorizeHttpRequests(auth -> auth
                                //Allow public access to specific endpoints
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/auth/**",
                                        "/ws/**",
                                        "/redis/**",
                                        "/actuator/**",
                                        "/api-docs",
                                        "/api-docs/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/hallo/**",
                                        "/public/**"
                                )
                                .permitAll()

                                .anyRequest()
                                .authenticated()
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
