package com.agenda.agendacultural.config;

// Removed security imports: Components, SecurityRequirement, SecurityScheme
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Removed security scheme configuration
        // final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Agenda Cultural API")
                        .description("API para gerenciamento de eventos culturais, categorias, comentários e favoritos. (Segurança Removida)")
                        .version("v0.0.2") // Increment version to reflect changes
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
                // Removed .addSecurityItem and .components related to security
    }
}

