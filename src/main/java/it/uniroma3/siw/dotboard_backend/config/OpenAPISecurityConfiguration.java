package it.uniroma3.siw.dotboard_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Dotboard APIs",
                version = "${api.version}",
                contact = @Contact(name = "Marco Napoleone, Chellee Mae Remache dela Reyna, Francesco Gaudino", url = "https://github.com/Reyleen/dotboard_backend"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
                description = "Restful API endpoints for Dotboard services"
        ),
        servers = @Server(
                url = "${api.server.url}",
                description = "dev"
        )
)
public class OpenAPISecurityConfiguration {
}

