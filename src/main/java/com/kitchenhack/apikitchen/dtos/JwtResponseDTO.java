package com.kitchenhack.apikitchen.dtos;

public class JwtResponseDTO {

    private final String token;

    public JwtResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

