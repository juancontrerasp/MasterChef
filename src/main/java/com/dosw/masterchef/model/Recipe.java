package com.dosw.masterchef.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotNull(message = "El consecutivo es requerido")
    private Long consecutivo;

    @NotBlank(message = "El título es requerido")
    private String titulo;

    @NotEmpty(message = "Debe incluir al menos un ingrediente")
    private List<String> ingredientes;

    @NotEmpty(message = "Debe incluir al menos un paso de preparación")
    private List<String> pasos;

    @NotNull(message = "La información del chef es requerida")
    private Chef chef;

    private Integer temporada;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Chef {
        @NotBlank(message = "El nombre del chef es requerido")
        private String nombre;

        @NotNull(message = "El tipo de chef es requerido")
        private TipoChef tipo;
    }

    public enum TipoChef {
        TELEVIDENTE,
        PARTICIPANTE,
        CHEF_JURADO
    }
}