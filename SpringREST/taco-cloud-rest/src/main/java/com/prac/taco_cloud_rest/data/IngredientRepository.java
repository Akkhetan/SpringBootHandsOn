package com.prac.taco_cloud_rest.data;

import com.prac.taco_cloud_rest.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="http://tacocloud:8080")
public interface IngredientRepository
         extends CrudRepository<Ingredient, String> {

}
