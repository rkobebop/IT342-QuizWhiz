//package cit.edu.quizwhiz.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/login", "/register", "/user/create", "/user/login", "/css/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/flashcards", true)
//                        .permitAll()
//                )
//                .oauth2Login(oauth -> oauth
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/flashcards", true)
//                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(new OidcUserService()))
//                )
//                .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
