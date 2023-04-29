package dev.accessaid.AccessAid.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.accessaid.AccessAid.User.service.UserDetailsServiceImpl;
import dev.accessaid.AccessAid.security.jwt.JwtAuthEntryPoint;
import dev.accessaid.AccessAid.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    JwtAuthEntryPoint unauthorizedHandler() {
        return new JwtAuthEntryPoint();
    }

    @Bean
    JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
