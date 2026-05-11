package com.kitchenhack.apikitchen.securities;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configura Swagger para mostrar el botón "Authorize 🔒" con soporte de Bearer token
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        // Nombre del esquema de seguridad que se referencia en los endpoints
        final String schemeName = "Bearer Authentication";

        return new OpenAPI()
                .info(new Info()
                        .title("KitchenHack API")
                        .version("1.0")
                        .description("API REST para la plataforma KitchenHack"))
                // Aplica el esquema de seguridad a todos los endpoints por defecto
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT") // Solo informativo para Swagger
                        ));
    }
}
