package com.dosw.masterchef.controller;

import com.dosw.masterchef.dto.RecipeRequestDTO;
import com.dosw.masterchef.model.Recipe;
import com.dosw.masterchef.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@Tag(name = "Recetas", description = "API de gestión de recetas de Master Chef Celebrity")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/televidente")
    @Operation(summary = "Registrar receta de televidente")
    public ResponseEntity<Recipe> crearRecetaTelespectador(
            @Valid @RequestBody RecipeRequestDTO request) {
        Recipe recipe = recipeService.crearRecetaTelespectador(request);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PostMapping("/participante")
    @Operation(summary = "Registrar receta de participante")
    public ResponseEntity<Recipe> crearRecetaParticipante(
            @Valid @RequestBody RecipeRequestDTO request) {
        Recipe recipe = recipeService.crearRecetaParticipante(request);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PostMapping("/chef-jurado")
    @Operation(summary = "Registrar receta de chef jurado")
    public ResponseEntity<Recipe> crearRecetaChefJurado(
            @Valid @RequestBody RecipeRequestDTO request) {
        Recipe recipe = recipeService.crearRecetaChefJurado(request);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las recetas")
    public ResponseEntity<List<Recipe>> obtenerTodasLasRecetas() {
        List<Recipe> recipes = recipeService.obtenerTodasLasRecetas();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{consecutivo}")
    @Operation(summary = "Obtener receta por consecutivo")
    public ResponseEntity<Recipe> obtenerRecetaPorConsecutivo(
            @PathVariable Long consecutivo) {
        Recipe recipe = recipeService.obtenerRecetaPorConsecutivo(consecutivo);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/participantes")
    @Operation(summary = "Obtener recetas de participantes")
    public ResponseEntity<List<Recipe>> obtenerRecetasParticipantes() {
        List<Recipe> recipes = recipeService.obtenerRecetasParticipantes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/televidentes")
    @Operation(summary = "Obtener recetas de televidentes")
    public ResponseEntity<List<Recipe>> obtenerRecetasTelevidentes() {
        List<Recipe> recipes = recipeService.obtenerRecetasTelevidentes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/chefs-jurados")
    @Operation(summary = "Obtener recetas de chefs jurados")
    public ResponseEntity<List<Recipe>> obtenerRecetasChefsJurados() {
        List<Recipe> recipes = recipeService.obtenerRecetasChefsJurados();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/temporada/{temporada}")
    @Operation(summary = "Obtener recetas por temporada")
    public ResponseEntity<List<Recipe>> obtenerRecetasPorTemporada(
            @PathVariable Integer temporada) {
        List<Recipe> recipes = recipeService.obtenerRecetasPorTemporada(temporada);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar recetas por ingrediente")
    public ResponseEntity<List<Recipe>> buscarRecetasPorIngrediente(
            @RequestParam String ingrediente) {
        List<Recipe> recipes = recipeService.buscarRecetasPorIngrediente(ingrediente);
        return ResponseEntity.ok(recipes);
    }

    @DeleteMapping("/{consecutivo}")
    @Operation(summary = "Eliminar receta")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long consecutivo) {
        recipeService.eliminarReceta(consecutivo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{consecutivo}")
    @Operation(summary = "Actualizar receta")
    public ResponseEntity<Recipe> actualizarReceta(
            @PathVariable Long consecutivo,
            @Valid @RequestBody RecipeRequestDTO request) {
        Recipe recipe = recipeService.actualizarReceta(consecutivo, request);
        return ResponseEntity.ok(recipe);
    }
}