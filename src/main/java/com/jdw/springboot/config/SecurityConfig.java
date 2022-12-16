package com.jdw.springboot.config;

import com.jdw.springboot.properties.UserAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserAuthProperties userAuthProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator", "/actuator/**", "/v3/api-docs/actuator").hasRole("ACTUATOR")
                        .requestMatchers("/swagger-ui", "/swagger-ui/**").hasRole("SWAGGER")
                        //.requestMatchers("/sys/user", "/sys/user/**", "/v3/api-docs/sys/user").hasRole("SYS_USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(SecurityProperties securityProperties) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        Map<String, UserAuthProperties.User> userMap = userAuthProperties.getUser();
        if (!userMap.isEmpty()) userMap.remove(securityProperties.getUser().getName());

        userMap.forEach((key, user) ->
                inMemoryUserDetailsManager.createUser(withDefaultPasswordEncoder()
                        .username(user.getName())
                        .password(user.getPassword())
                        .roles(user.getRoles().toArray(new String[0]))
                        .build()));
        return inMemoryUserDetailsManager;
    }
}
