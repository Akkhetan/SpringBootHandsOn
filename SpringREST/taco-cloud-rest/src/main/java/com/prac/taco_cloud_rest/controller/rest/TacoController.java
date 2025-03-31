package com.prac.taco_cloud_rest.controller.rest;

import com.prac.taco_cloud_rest.data.TacoRepository;
import com.prac.taco_cloud_rest.entity.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path="/api/tacos", produces="application/json")

public class TacoController {
  private TacoRepository tacoRepo;

  public TacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping(params="recent") ///api/tacos?recent
  public Iterable<Taco> recentTacos() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }


  @PostMapping(consumes="application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
      return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }


  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/deleteAll")
  public void deleteAllTacoes() {
    tacoRepo.deleteAll();
  }

}