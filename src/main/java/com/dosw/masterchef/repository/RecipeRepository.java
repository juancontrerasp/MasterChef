package com.dosw.masterchef.repository;

import com.dosw.masterchef.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    Optional<Recipe> findByConsecutivo(Long consecutivo);

    List<Recipe> findByChefTipo(Recipe.TipoChef tipo);

    List<Recipe> findByTemporada(Integer temporada);

    @Query("{ 'ingredientes': { $regex: ?0, $options: 'i' } }")
    List<Recipe> findByIngredienteContaining(String ingrediente);
}