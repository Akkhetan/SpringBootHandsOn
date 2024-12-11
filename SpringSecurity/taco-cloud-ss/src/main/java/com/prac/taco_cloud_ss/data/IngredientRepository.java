package com.prac.taco_cloud_ss.data;

import com.prac.taco_cloud_ss.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
  
}
