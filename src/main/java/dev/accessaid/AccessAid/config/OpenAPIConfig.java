package dev.accessaid.AccessAid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

        @Value("http://localhost:8080")
        private String devUrl;

        @Value("${api.pagination.default-size}")
        private int defaultPageSize;

        final String securitySchemeName = "bearerAuth";

        @Bean
        public OpenAPI openAPI() {
                Pageable defaultPageable = PageRequest.of(0, defaultPageSize);

                return new OpenAPI()
                                .info(new io.swagger.v3.oas.models.info.Info()
                                                .title("AccessAid API")
                                                .description("AccessAid API")
                                                .version("1.0.0")
                                                .contact(new io.swagger.v3.oas.models.info.Contact()
                                                                .url("https://github.com/AccessAid")
                                                                .name("AccessAid Team"))
                                                .license(new io.swagger.v3.oas.models.info.License()
                                                                .name("Apache 2.0")
                                                                .url("http://www.apache.org/licenses/LICENSE-2.0")))
                                .addSecurityItem(new SecurityRequirement()
                                                .addList(securitySchemeName))
                                .components(new Components()
                                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                                                .name(securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
                // .extensions(Collections
                // .singletonList(new OpenApiCustomPageableExtension(defaultPageable)));

        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(CorsRegistry registry) {
                                registry.addMapping("/**")
                                                .allowedOrigins("*")
                                                .allowedMethods("GET", "POST", "PUT", "DELETE")
                                                .allowedHeaders("*");
                        }
                };
        }
}
