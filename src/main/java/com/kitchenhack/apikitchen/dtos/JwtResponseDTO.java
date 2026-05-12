package com.kitchenhack.apikitchen.dtos;

import java.io.Serializable;

/**
 * DTO de respuesta para /login que contiene el token JWT generado.
 */
public class JwtResponseDTO implements Serializable {

    private final String jwttoken;

    public String getJwttoken() {
        return jwttoken;
    }

    public JwtResponseDTO(String jwttoken) {
        super();
        this.jwttoken = jwttoken;
    }

}