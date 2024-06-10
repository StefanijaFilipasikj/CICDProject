package mk.ukim.finki.wp.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig{
    private final CustomUsernamePasswordAuthProvider usernamePasswordAuthProvider;

    public WebSecurityConfig(CustomUsernamePasswordAuthProvider usernamePasswordAuthProvider) {
        this.usernamePasswordAuthProvider = usernamePasswordAuthProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/assets/**", "/register", "/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .failureForwardUrl("/login?error=BadCredentials")
                        .defaultSuccessUrl("/products"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login"))
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access_denied"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthProvider);
        return authenticationManagerBuilder.build();
    }
}