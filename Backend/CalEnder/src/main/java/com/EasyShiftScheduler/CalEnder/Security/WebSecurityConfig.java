package com.EasyShiftScheduler.CalEnder.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class WebSecurityConfig {
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Updated configuration for Spring Security 6.x
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .cors(cors -> cors.disable()) // Disable CORS (or configure if needed)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                // Add protected API URLs here
                                .requestMatchers("/api/group/create-group").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/group/publishSchedule").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/group/generateReport").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/group/getGroups").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/schedule/work-schedule/delete").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/schedule/create-schedule").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/schedule/update-schedule").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/schedule/work-schedule/auto").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/time-off-request/approve").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/time-off-request/deny").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/user/compensation-rate").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/user/user_timecard/update").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/user_timecard/worked_hours").hasAuthority("EMPLOYER")
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().hasAnyAuthority("EMPLOYER", "EMPLOYEE")

                );
        // Add the JWT Token filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
