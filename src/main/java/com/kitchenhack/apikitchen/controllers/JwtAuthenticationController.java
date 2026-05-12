package com.kitchenhack.apikitchen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kitchenhack.apikitchen.dtos.JwtRequestDTO;
import com.kitchenhack.apikitchen.dtos.JwtResponseDTO;
import com.kitchenhack.apikitchen.securities.JwtTokenUtil;


/**
 * Controlador que expone el endpoint de autenticación (/login).
 *
 * Flujo:
 * 1. Valida credenciales vía AuthenticationManager
 * 2. Carga UserDetails y genera un JWT con {@link JwtTokenUtil}
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private com.kitchenhack.apikitchen.servicesimplements.JwtUserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO req) throws Exception {
        // 1) Autenticar usando AuthenticationManager (lanza excepción si falla)
        authenticate(req.getUsername(), req.getPassword());

        // 2) Cargar detalles del usuario y generar token JWT que incluye roles
        final org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    /**
     * Intenta autenticar al usuario con las credenciales proporcionadas.
     *
     * @throws Exception si el usuario está deshabilitado o las credenciales son inválidas
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}