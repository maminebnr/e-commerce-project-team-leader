package tn.clevory.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ecommerceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce Products API")
                        .description("API de gestion des produits — Formation Clevory (API-First)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Clevory Training")
                                .email("contact@clevory.tn"))
                        .license(new License().name("Proprietary")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Spring Boot"),
                        new Server().url("http://localhost:4010").description("Prism mock")
                ));
    }
}
