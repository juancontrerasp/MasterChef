package com.dosw.masterchef.service;

import com.dosw.masterchef.dto.RecipeRequestDTO;
import com.dosw.masterchef.exception.ResourceNotFoundException;
import com.dosw.masterchef.model.Recipe;
import com.dosw.masterchef.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @InjectMocks
    private RecipeService recipeService;

    private RecipeRequestDTO requestDTO;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        requestDTO = new RecipeRequestDTO();
        requestDTO.setTitulo("Arroz con Pollo");
        requestDTO.setIngredientes(Arrays.asList("Arroz", "Pollo", "Zanahoria"));
        requestDTO.setPasos(Arrays.asList("Paso 1", "Paso 2", "Paso 3"));
        requestDTO.setNombreChef("Juan Pérez");

        recipe = new Recipe();
        recipe.setConsecutivo(1L);
        recipe.setTitulo("Arroz con Pollo");
        recipe.setIngredientes(Arrays.asList("Arroz", "Pollo", "Zanahoria"));
        recipe.setPasos(Arrays.asList("Paso 1", "Paso 2", "Paso 3"));

        Recipe.Chef chef = new Recipe.Chef();
        chef.setNombre("Juan Pérez");
        chef.setTipo(Recipe.TipoChef.TELEVIDENTE);
        recipe.setChef(chef);
    }

    @Test
    void testCrearRecetaTelespectador() {

        when(sequenceGenerator.generateSequence(anyString())).thenReturn(1L);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);


        Recipe result = recipeService.crearRecetaTelespectador(requestDTO);


        assertNotNull(result);
        assertEquals("Arroz con Pollo", result.getTitulo());
        assertEquals(Recipe.TipoChef.TELEVIDENTE, result.getChef().getTipo());
        assertEquals("Juan Pérez", result.getChef().getNombre());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testCrearRecetaParticipante() {
        requestDTO.setTemporada(1);
        recipe.getChef().setTipo(Recipe.TipoChef.PARTICIPANTE);
        recipe.setTemporada(1);

        when(sequenceGenerator.generateSequence(anyString())).thenReturn(1L);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe result = recipeService.crearRecetaParticipante(requestDTO);


        assertNotNull(result);
        assertEquals(Recipe.TipoChef.PARTICIPANTE, result.getChef().getTipo());
        assertEquals(1, result.getTemporada());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testCrearRecetaParticipanteSinTemporada() {
        requestDTO.setTemporada(null);

        assertThrows(IllegalArgumentException.class, () -> {
            recipeService.crearRecetaParticipante(requestDTO);
        });
    }

    @Test
    void testBuscarRecetasPorIngrediente() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByIngredienteContaining("Arroz")).thenReturn(recipes);

        List<Recipe> result = recipeService.buscarRecetasPorIngrediente("Arroz");


        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getIngredientes().contains("Arroz"));
        verify(recipeRepository, times(1)).findByIngredienteContaining("Arroz");
    }

    @Test
    void testObtenerRecetaPorConsecutivoExistente() {
        when(recipeRepository.findByConsecutivo(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.obtenerRecetaPorConsecutivo(1L);


        assertNotNull(result);
        assertEquals(1L, result.getConsecutivo());
        verify(recipeRepository, times(1)).findByConsecutivo(1L);
    }

    @Test
    void testObtenerRecetaPorConsecutivoInexistente() {
        when(recipeRepository.findByConsecutivo(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            recipeService.obtenerRecetaPorConsecutivo(999L);
        });
        verify(recipeRepository, times(1)).findByConsecutivo(999L);
    }

    @Test
    void testObtenerTodasLasRecetas() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> result = recipeService.obtenerTodasLasRecetas();


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testEliminarReceta() {
        when(recipeRepository.findByConsecutivo(1L)).thenReturn(Optional.of(recipe));
        doNothing().when(recipeRepository).delete(recipe);

        recipeService.eliminarReceta(1L);


        verify(recipeRepository, times(1)).findByConsecutivo(1L);
        verify(recipeRepository, times(1)).delete(recipe);
    }

    @Test
    void testActualizarReceta() {
        when(recipeRepository.findByConsecutivo(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeRequestDTO updateDTO = new RecipeRequestDTO();
        updateDTO.setTitulo("Arroz con Pollo Actualizado");
        updateDTO.setIngredientes(Arrays.asList("Arroz", "Pollo", "Zanahoria", "Arveja"));
        updateDTO.setPasos(Arrays.asList("Paso 1", "Paso 2", "Paso 3", "Paso 4"));
        updateDTO.setNombreChef("Juan Pérez");

        Recipe result = recipeService.actualizarReceta(1L, updateDTO);


        assertNotNull(result);
        verify(recipeRepository, times(1)).findByConsecutivo(1L);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}