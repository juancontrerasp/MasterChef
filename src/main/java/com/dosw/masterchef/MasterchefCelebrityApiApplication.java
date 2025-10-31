package com.dosw.masterchef;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Master Chef Celebrity API",
                version = "1.0.0",
                description = "API de gestión de recetas de cocina para el programa Master Chef Celebrity"
        )
)
public class MasterchefCelebrityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterchefCelebrityApiApplication.class, args);
    }
}