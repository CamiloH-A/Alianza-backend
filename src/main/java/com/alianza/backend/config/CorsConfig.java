package com.alianza.backend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Ajusta el patrón de URL según tus necesidades
                .allowedOrigins("http://localhost:4200") // Ajusta el origen permitido según tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Ajusta los métodos HTTP permitidos según tus necesidades
                .allowedHeaders("*"); // Puedes ajustar los encabezados permitidos según tus necesidades
    }
}

