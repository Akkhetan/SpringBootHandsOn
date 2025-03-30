package com.prac.taco_cloud_rest.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
//@RestResource(rel="tacos", path="tacos")
public class Taco {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "taco_seq")
  @SequenceGenerator(name = "taco_seq", sequenceName = "taco_seq", allocationSize = 1)
  private Long id;
  
  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;
  
  private Date createdAt;

  @ManyToMany(targetEntity=Ingredient.class)
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<Ingredient> ingredients = new ArrayList<>();

  @PrePersist
  void createdAt() {
    this.createdAt = new Date();
  }
  
  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
  }
}