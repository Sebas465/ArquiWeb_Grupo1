package com.kitchenhack.apikitchen.dtos;

import java.io.Serializable;

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