package com.kitchenhack.apikitchen.securities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * Punto de entrada para peticiones no autenticadas. Se utiliza para devolver
 * un 401 controlado cuando falla la autenticación (por ejemplo token ausente
 * o inválido).
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Envía un 401 (Unauthorized) cuando la autenticación no se cumple.
     *
     * @param request the request
     * @param response the response
     * @param authException exception lanzada por el mecanismo de autenticación
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
