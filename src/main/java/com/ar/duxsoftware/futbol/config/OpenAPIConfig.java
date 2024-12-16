package com.ar.duxsoftware.futbol.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    private static final String TITLE = "Documentación";
    private static final String DESCRIPCION = "Documentación de la API de fútbol";
    private static final String VERSION = "1.0.0";

    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "BearerAuth";
        final SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        final Components components = new Components()
                .addSecuritySchemes(securitySchemeName, securityScheme);
        final SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title(TITLE)
                        .description(DESCRIPCION)
                        .version(VERSION)
                );
    }
}
