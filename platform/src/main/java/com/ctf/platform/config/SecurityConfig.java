package com.ctf.platform.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*Apagar CSRF
      Volver al servidor "Amnesico" (Stateless)
      indicar que endpoints son publicos y cuales no
     **/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hints/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/challenges").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/challenges").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/hints").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/solves").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }



        /*
        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request.anyRequest()
                        .authenticated()).httpBasic(Customizer.withDefaults())
                        .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
*/
}
