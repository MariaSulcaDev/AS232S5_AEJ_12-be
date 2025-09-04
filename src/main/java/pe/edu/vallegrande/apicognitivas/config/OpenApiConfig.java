package pe.edu.vallegrande.apicognitivas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

     @Bean
     public OpenAPI customOpenAPI() {
          return new OpenAPI()
                    .info(new Info()
                              .title("APIs Cognitivas - Valle Grande")
                              .description(
                                        "API REST para servicios cognitivos: traducción de texto y descarga de información de videos de YouTube")
                              .version("1.0.0")
                              .contact(new Contact()
                                        .name("Valle Grande Team")
                                        .email("support@vallegrande.edu.pe")))
                    .servers(List.of(
                              new Server()
                                        .url("http://localhost:8081")
                                        .description("Servidor de desarrollo local")));
     }
}
