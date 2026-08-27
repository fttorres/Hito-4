package com.neonpulse.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NeonPulse API")
                        .version("1.0.0")
                        .description("Documentación de la API de NeonPulse para la venta de tickets de concertos (Hito 3).")
                        .contact(new Contact()
                                .name("NeonPulse Team")
                        )
                );
    }
}
