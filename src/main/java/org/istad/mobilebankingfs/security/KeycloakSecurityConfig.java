package org.istad.mobilebankingfs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {


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
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**")
                        .hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**")
                        .hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**")
                        .hasAnyRole("ADMIN","STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/**")
                        .hasAnyRole("ADMIN","STAFF", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts/**")
                        .hasAnyRole("ADMIN","STAFF", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts/**")
                        .hasAnyRole("ADMIN","STAFF", "USER")
                        .requestMatchers("/api/v1/media/**")
                        .permitAll()
                        .requestMatchers("/media/**")
                        .permitAll()
                        .requestMatchers("/api/v1/auth/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        );

        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism to defaults brower
//        http.httpBasic(Customizer.withDefaults());


        // change to get jwt security
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults()));

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

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }

}
