package com.demo.JWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService customerUserDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService customerUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // For testing only; replace with a stronger encoder in production
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/bill/generate").hasRole("USER")
                                
                                .requestMatchers("/","/user/login", "/user/signup", "/user/get", "/user/admin", "/orders/get", "/orders/status/**", "/user/forgotPassword","/category/get","/category/add","/category/update","/category/delete/**","/product/get","/product/add","/product/update/**","/product/delete/**","/product/update-status/**","/deliveries/get","/delivery/all","/delivery/**","/delivery/status/**","/deliveries/add","/images/**","/table-login/generate-qr/**","/table-login/add","/table-login/auto-login/**","/orders/placeOrder","/orders/getByShopId/**","/delivery/placeOrder","/delivery/getByEmail","/delivery/getOrderByShop/**","/bill/generate","/table-login/edit/**","/table-login/all","/table-login/status/**","/table-login/update-status/**","/shop/get","/shop/add","/shop/update","/shop/delete/**", "/account/create", "/account/all", "/account/update/**", "/account/delete/**", "/account/login", "/account/id/**", "/table-login/grouped-by-shop", "/table-login/shop/**","/table-login/all", "shop/shop-name/**","/product/shop/**").permitAll()
//                                .requestMatchers("/user/login", "/user/signup", "/user/forgotPassword","/category/get","/product/get","/deliveries/get","/deliveries/add","/images/**","/table-login/auto-login","/table-login/add","/qrcode/generate").permitAll()

                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//    return http.build();
//}

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(List.of("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("http://localhost:517*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true); // ✅ required if you're using cookies

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}


//    @Configuration
//    @EnableWebMvc
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:5173") // frontend URL
//                    .allowedMethods("GET", "POST", "PUT", "DELETE")
//                    .allowedHeaders("*");
//        }
//    }

}
