package com.dosw.masterchef.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDTO {

    @NotBlank(message = "El título es requerido")
    private String titulo;

    @NotEmpty(message = "Debe incluir al menos un ingrediente")
    private List<String> ingredientes;

    @NotEmpty(message = "Debe incluir al menos un paso de preparación")
    private List<String> pasos;

    @NotBlank(message = "El nombre del chef es requerido")
    private String nombreChef;

    private Integer temporada;
}