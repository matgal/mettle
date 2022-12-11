package dev.m8code.mettle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true)
                .ignoring()
                .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
                .requestMatchers("/h2-console/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin =
                User.withUsername("admin")
                        .password(passwordEncoder().encode("password"))
                        .roles("ADMIN")
                        .build();

        UserDetails user1 =
                User.withUsername("user1")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.withUsername("user2")
                        .password(passwordEncoder().encode("password"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(admin, user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
