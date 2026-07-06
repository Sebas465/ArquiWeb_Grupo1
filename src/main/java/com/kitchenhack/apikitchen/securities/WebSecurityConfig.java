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
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuración recomendada para API REST + JWT
        httpSecurity.cors(Customizer.withDefaults()) // ¡Importante para que Spring Security integre CorsConfig!
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/usuarios/nuevo", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // P1 — Gestión (solo admin)
                        .requestMatchers("/roles/**", "/usuarios/**").hasAuthority("admin")
                        // P2 — Recetas: catálogo (nutricionista arma el contenido, admin todo)
                        .requestMatchers("/etiquetas/**", "/ingredientes/**").hasAnyAuthority("nutricionista", "admin")
                        // P4 — Planes maestros: alimenticios (nutricionista) y de ejercicio (entrenador)
                        .requestMatchers("/planes/**").hasAnyAuthority("nutricionista", "entrenador", "admin")
                        // /api/recipes/** y /ejercicios/** solo exigen estar logueado aquí:
                        // el detalle de lectura vs. escritura lo resuelve @PreAuthorize en cada método
                        // (RecipeController y EjercicioController), porque dentro del mismo módulo
                        // "ver" y "crear/editar/borrar" tienen roles distintos.
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
