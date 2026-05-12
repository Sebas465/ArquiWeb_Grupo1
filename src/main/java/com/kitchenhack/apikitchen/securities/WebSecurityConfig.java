package com.kitchenhack.apikitchen.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//@Profile(value = {"development", "production"})
//Clase S7
/**
 * Configuración central de seguridad para la aplicación.
 *
 * - Define el bean {@link PasswordEncoder} usado para codificar contraseñas.
 * - Registra el {@link UserDetailsService} para la autenticación.
 * - Configura la cadena de filtros HTTP para una API REST basada en JWT:
 *   - CSRF deshabilitado
 *   - SessionCreationPolicy.STATELESS (no se mantienen sesiones en servidor)
 *   - Endpoints públicos: /login, /usuarios/nuevo, y Swagger
 *   - Uso de {@link JwtAuthenticationEntryPoint} para devolver 401 controlados
 *   - Registro de {@link JwtRequestFilter} antes de {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // ...existing code... (removed unused HandlerExceptionResolver injection)

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Delega la creación del AuthenticationManager a la configuración de Spring
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public static PasswordEncoder passwordEncoder() {
        // Bean global para codificar/validar contraseñas con BCrypt
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Configura el UserDetailsService y el PasswordEncoder que se usarán
        // cuando Spring realice la autenticación (delegado al AuthenticationManager)
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuración recomendada para API REST + JWT
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                // No crear/usar sesión en servidor: cada petición se autentica por token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/login", "/usuarios/nuevo", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        // Registrar filtro JWT antes del filtrado estándar de Spring Security
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
