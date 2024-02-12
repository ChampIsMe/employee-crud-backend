package com.phil.employeecrudbackend.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Employee API",
        version = "0.0.1",
//        contact = @Contact(name = "Philip", email = "po@example.com", url = "https://www.example.com"),
//        license = @License(name = "Apache 2.0", url = ""),
//        termsOfService = "${tos.uri}",
        description = "Documentation for Employee API backend"
    ),
    servers = @Server(url = "/", description = "Development")
)
//todo: Uncomment when auth is implemented
/*@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)*/
public class OpenAPI30Configuration {
}
