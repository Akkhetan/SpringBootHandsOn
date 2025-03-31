package com.prac.taco_cloud_rest.config;

import com.prac.taco_cloud_rest.data.IngredientRepository;
import com.prac.taco_cloud_rest.data.TacoRepository;
import com.prac.taco_cloud_rest.data.UserRepository;
import com.prac.taco_cloud_rest.entity.Ingredient.Type;
import com.prac.taco_cloud_rest.entity.Ingredient;
import com.prac.taco_cloud_rest.entity.Taco;
import com.prac.taco_cloud_rest.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

//@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(
      IngredientRepository repo,
      UserRepository userRepo,
      PasswordEncoder encoder,
      TacoRepository tacoRepo) {
    return args -> {
      Ingredient flourTortilla = new Ingredient(
          "FLTO", "Flour Tortilla", Type.WRAP);
      Ingredient cornTortilla = new Ingredient(
          "COTO", "Corn Tortilla", Type.WRAP);
      Ingredient groundBeef = new Ingredient(
          "GRBF", "Ground Beef", Type.PROTEIN);
      Ingredient carnitas = new Ingredient(
          "CARN", "Carnitas", Type.PROTEIN);
      Ingredient tomatoes = new Ingredient(
          "TMTO", "Diced Tomatoes", Type.VEGGIES);
      Ingredient lettuce = new Ingredient(
          "LETC", "Lettuce", Type.VEGGIES);
      Ingredient cheddar = new Ingredient(
          "CHED", "Cheddar", Type.CHEESE);
      Ingredient jack = new Ingredient(
          "JACK", "Monterrey Jack", Type.CHEESE);
      Ingredient salsa = new Ingredient(
          "SLSA", "Salsa", Type.SAUCE);
      Ingredient sourCream = new Ingredient(
          "SRCR", "Sour Cream", Type.SAUCE);
      repo.save(flourTortilla);
      repo.save(cornTortilla);
      repo.save(groundBeef);
      repo.save(carnitas);
      repo.save(tomatoes);
      repo.save(lettuce);
      repo.save(cheddar);
      repo.save(jack);
      repo.save(salsa);
      repo.save(sourCream);


      if(userRepo.findByUsername("ankit") == null){
        userRepo.save(new User("ankit", encoder.encode("password"),
                "ROLE_ADMIN", "Ankit Khetan","123 North Street", "Cross Roads", "TX",
                "76227", "123-123-123"));
      }

      if(userRepo.findByUsername("santosh") == null){
        userRepo.save(new User("santosh", encoder.encode("password"),
                "ROLE_USER,ROLE_ADMIN","Santosh Khetan", "123 North Street", "Cross Roads", "TX",
                "76227", "123-123-1234"));
      }

      if(userRepo.findByUsername("ankit") == null){
        userRepo.save(new User("deepak", encoder.encode("password"),
                "ROLE_USER","Deepak Khetan", "123 North Street", "Cross Roads", "TX",
                "76227", "123-123-1234"));
      }

      Taco taco1 = new Taco();
      taco1.setName("Carnivore");
      taco1.setIngredients(Arrays.asList(
              flourTortilla, groundBeef, carnitas,
              sourCream, salsa, cheddar));
      tacoRepo.save(taco1);

      Taco taco2 = new Taco();
      taco2.setName("Bovine Bounty");
      taco2.setIngredients(Arrays.asList(
              cornTortilla, groundBeef, cheddar,
              jack, sourCream));
      tacoRepo.save(taco2);

      Taco taco3 = new Taco();
      taco3.setName("Veg-Out");
      taco3.setIngredients(Arrays.asList(
              flourTortilla, cornTortilla, tomatoes,
              lettuce, salsa));
      tacoRepo.save(taco3);
    };
  }
  
}