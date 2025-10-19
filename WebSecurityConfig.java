package com.Online.ParkigManagement.Security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Online.ParkigManagement.Repository.RoleRepository;
import com.Online.ParkigManagement.Repository.UserRepository;
import com.Online.ParkigManagement.Security.JWT.AuthEnteryPointJwt;
import com.Online.ParkigManagement.Security.JWT.AuthTokenFilter;
import com.Online.ParkigManagement.Security.ServiceSecurity.UserDetaileServiceImpl;
import com.Online.ParkigManagement.model.AppRole;
import com.Online.ParkigManagement.model.Role;
import com.Online.ParkigManagement.model.User;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetaileServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepositroy;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthEnteryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                //.requestMatchers("/api/admin/**").permitAll()
                               // .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(
                frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"));
    }


    @Bean
    CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.Role_Driver)
            .orElseGet(() -> {
                Role newUserRole = new Role(AppRole.Role_Driver);
                return roleRepository.save(newUserRole);
            });
            Role adminRole = roleRepository.findByRoleName(AppRole.Role_ADMIN)
            .orElseGet(() -> {
                Role newAdminRole = new Role(AppRole.Role_ADMIN);
                return roleRepository.save(newAdminRole);
            });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> adminRoles = Set.of(userRole, adminRole);

            if (!userRepository.existsByEmail("user1@example.com")) {
                User user1 = new User(
                 
                    "user1@example.com",
                    passwordEncoder.encode("password1")
                );
                userRepository.save(user1);
            }
            
            if (!userRepository.existsByEmail("user1@example.com")) {
            User admin = new User(
                 
                    "Admin@example.com",
                    passwordEncoder.encode("password")
                );
                userRepository.save(admin);
            }



        };
    }
}