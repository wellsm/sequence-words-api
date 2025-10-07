package com.well.sequencewordsapi.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class OpenApiCustomConfig {

    @Bean
    @Primary
    fun openApi(): OpenAPI =
        OpenAPI()
            .info(Info().title("Sequence Words API").version("1.0.0"))
            .components(
                Components().addSecuritySchemes(
                    "jwt",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Send: Authorization: Bearer <token>")
                )
            )
            .addSecurityItem(SecurityRequirement().addList("jwt"))
}

