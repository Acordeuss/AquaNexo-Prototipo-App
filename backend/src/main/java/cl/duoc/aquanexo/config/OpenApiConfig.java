package cl.duoc.aquanexo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.components.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI api() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("AquaNexo API")
                    .version("1.0")
                    .description("API académica de sincronización de pre-chequeos de buceo")
            )
            .components(
                new Components().addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                )
            );
    }
}
