package org.istad.mobilebankingfs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_CUSTOMER = "USER";

    private final PasswordEncoder passwordEncoder;
    private final SecurityBean securityBean;
    private final UserDetailServiceImpl userDetailsService;


    //
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("qwer@123"))
//                .roles("ADMIN")
//                .build();
//        manager.createUser(admin);
//        UserDetails staff = User.builder()
//                .username("staff")
//                .password(passwordEncoder.encode("qwer@123"))
//                .roles("STAFF")
//                .build();
//        manager.createUser(staff);
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("qwer@123"))
//                .roles("USER")
//                .build();
//        manager.createUser(user);
//        return manager;
//    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // TODO

        // make all endpoints secured
        http.authorizeHttpRequests(endpoint ->
                endpoint
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/**").hasAnyRole("ADMIN","STAFF", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts/**").hasAnyRole("ADMIN","STAFF", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts/**").hasAnyRole("ADMIN","STAFF", "USER")
                        .anyRequest()
                        .authenticated()
                );

        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism
        http.httpBasic(Customizer.withDefaults());

        // CSRF common protection
        http.csrf(csrf -> csrf.disable());

        // make stateless
        http.sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );

        return http.build();
    }
}
