package com.dosw.masterchef.service;

import com.dosw.masterchef.dto.RecipeRequestDTO;
import com.dosw.masterchef.exception.ResourceNotFoundException;
import com.dosw.masterchef.model.Recipe;
import com.dosw.masterchef.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    private static final String SEQUENCE_NAME = "recipes_sequence";

    public Recipe crearRecetaTelespectador(RecipeRequestDTO request) {
        Recipe recipe = new Recipe();
        recipe.setConsecutivo(sequenceGenerator.generateSequence(SEQUENCE_NAME));
        recipe.setTitulo(request.getTitulo());
        recipe.setIngredientes(request.getIngredientes());
        recipe.setPasos(request.getPasos());

        Recipe.Chef chef = new Recipe.Chef();
        chef.setNombre(request.getNombreChef());
        chef.setTipo(Recipe.TipoChef.TELEVIDENTE);
        recipe.setChef(chef);

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    public Recipe crearRecetaParticipante(RecipeRequestDTO request) {
        if (request.getTemporada() == null) {
            throw new IllegalArgumentException("La temporada es requerida para participantes");
        }

        Recipe recipe = new Recipe();
        recipe.setConsecutivo(sequenceGenerator.generateSequence(SEQUENCE_NAME));
        recipe.setTitulo(request.getTitulo());
        recipe.setIngredientes(request.getIngredientes());
        recipe.setPasos(request.getPasos());
        recipe.setTemporada(request.getTemporada());

        Recipe.Chef chef = new Recipe.Chef();
        chef.setNombre(request.getNombreChef());
        chef.setTipo(Recipe.TipoChef.PARTICIPANTE);
        recipe.setChef(chef);

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    public Recipe crearRecetaChefJurado(RecipeRequestDTO request) {
        Recipe recipe = new Recipe();
        recipe.setConsecutivo(sequenceGenerator.generateSequence(SEQUENCE_NAME));
        recipe.setTitulo(request.getTitulo());
        recipe.setIngredientes(request.getIngredientes());
        recipe.setPasos(request.getPasos());

        Recipe.Chef chef = new Recipe.Chef();
        chef.setNombre(request.getNombreChef());
        chef.setTipo(Recipe.TipoChef.CHEF_JURADO);
        recipe.setChef(chef);

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    public List<Recipe> obtenerTodasLasRecetas() {
        return recipeRepository.findAll();
    }

    public Recipe obtenerRecetaPorConsecutivo(Long consecutivo) {
        return recipeRepository.findByConsecutivo(consecutivo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Receta no encontrada con consecutivo: " + consecutivo));
    }

    public List<Recipe> obtenerRecetasParticipantes() {
        return recipeRepository.findByChefTipo(Recipe.TipoChef.PARTICIPANTE);
    }

    public List<Recipe> obtenerRecetasTelevidentes() {
        return recipeRepository.findByChefTipo(Recipe.TipoChef.TELEVIDENTE);
    }

    public List<Recipe> obtenerRecetasChefsJurados() {
        return recipeRepository.findByChefTipo(Recipe.TipoChef.CHEF_JURADO);
    }

    public List<Recipe> obtenerRecetasPorTemporada(Integer temporada) {
        return recipeRepository.findByTemporada(temporada);
    }

    public List<Recipe> buscarRecetasPorIngrediente(String ingrediente) {
        return recipeRepository.findByIngredienteContaining(ingrediente);
    }

    public void eliminarReceta(Long consecutivo) {
        Recipe recipe = obtenerRecetaPorConsecutivo(consecutivo);
        recipeRepository.delete(recipe);
    }

    public Recipe actualizarReceta(Long consecutivo, RecipeRequestDTO request) {
        Recipe recipe = obtenerRecetaPorConsecutivo(consecutivo);

        recipe.setTitulo(request.getTitulo());
        recipe.setIngredientes(request.getIngredientes());
        recipe.setPasos(request.getPasos());
        recipe.getChef().setNombre(request.getNombreChef());

        if (request.getTemporada() != null) {
            recipe.setTemporada(request.getTemporada());
        }

        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }
}