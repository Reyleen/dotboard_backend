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
        contact = @Contact(name = "Marco Napoleone, Chellee Mae Remacha Dela Reyna, Francesco Gaudino", url = "https://github.com/Reyleen/dotboard_backend"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        description = "Restful API endpoints for Dotboard services"
    ),
    servers = {
        @io.swagger.v3.oas.annotations.servers.Server(
            description = "Localhost",
            url = "http://localhost:8080"
        ),
        @io.swagger.v3.oas.annotations.servers.Server(
            description = "AWS EC2",
            url = "http://ec2-16-171-145-236.eu-north-1.compute.amazonaws.com/"
        )
    }
)
public class OpenAPISecurityConfiguration {
}

