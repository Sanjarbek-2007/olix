package uz.project.olix.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "OLIX",
                description = "This application is for monkey only",
                version = "${app.version}",
                contact = @Contact(
                        name = "OLIX",
                        url = "https://github.com/Sanjarbek-2007/olix/tree/development",
                        email = "olix@gmail.com"
                ),
                license = @License(
                        name = "Apache 3.0",
                        url = "https://starter.spring.io"
                ),
                termsOfService = "https://wwww.wiki.com",
                summary = "The project is fully supported by DS team"
        ),
        servers = {
                @Server(url = "http://localhost:8081/swagger" ,
                        description = "Development Server"),
        }
)
public class SwaggerConfiguration {
}