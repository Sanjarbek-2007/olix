package uz.project.olix.config.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true
)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .csrf(CsrfConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registr ->
                        registr.requestMatchers("/auth/**"
                                ).permitAll()
//                                .requestMatchers("/posts/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                ).sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails adminDetails = User.builder()
//                .username("donAdmin")
//                .password("donAdminPass")
//                .roles("ADMIN")
//                .build();
//        UserDetails userDetails = User.builder()
//                .username("donUser")
//                .password("donUserPass")
//                .roles("USER")
//                .build();
//        UserDetails ownerDetails = User.builder()
//                .username("donOwner")
//                .password("donOwnerPass")
//                .roles("OWNER")
//                .build();
//        UserDetails driverDetails = User.builder()
//                .username("donDriver")
//                .password(passwordEncoder().encode("donDriverPass"))
//                .roles("DRIVER")
//                .build();
//        UserDetails customerDetails = User.builder()
//                .username("donCustomer")
//                .password(passwordEncoder().encode("donCustomerPass"))
//                .roles("CUSTOMER")
//                .build();
//        return new InMemoryUserDetailsManager(adminDetails, userDetails, ownerDetails, driverDetails, customerDetails);
//    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("jwt auth"))
                .components(new Components().addSecuritySchemes("jwt auth", new SecurityScheme()
                                                .name("jwt auth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                                .scheme("bearer")));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}


