package com.myprojects.smartcontactmaneger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").hasRole("USER") // Use "ROLE_USER" for roles
                .requestMatchers("/admin/**").hasRole("ADMIN") // Example of admin protection
                .requestMatchers("/register", "/", "/home", "/about", "/signup","/css/**", "/js/**", "/img/**")
                .permitAll());

        http.formLogin(login -> login
                .loginPage("/signin")
                .loginProcessingUrl("/login") // The URL where the form submits
                .defaultSuccessUrl("/user/index")
                .failureUrl("/signin?error=true") // Handle login failures
                .permitAll());

        http.logout(logout -> logout
                .logoutUrl("/logout") // URL to trigger logout
                .logoutSuccessUrl("/signin?logout=true") // Redirect after successful logout
                .deleteCookies("JSESSIONID") // Optionally delete cookies
                .invalidateHttpSession(true) // Invalidate session
                .permitAll());

        // Enable CSRF for security, except for GET requests.
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/logout"));

        return http.build();
    }
}
