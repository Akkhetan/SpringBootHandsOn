package com.prac.taco_cloud_rest.controller.rest;

import com.prac.taco_cloud_rest.data.OrderRepository;
import com.prac.taco_cloud_rest.entity.TacoOrder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(path="/api/orders",
                produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
public class OrderApiController {

  private OrderRepository orderRepo;

  public OrderApiController(OrderRepository orderRepo) {
    this.orderRepo = orderRepo;
  }

  @GetMapping(produces="application/json")
  public Iterable<TacoOrder> allOrders() {
    return orderRepo.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TacoOrder> tacoById(@PathVariable("id") Long id) {
    Optional<TacoOrder> optTacoOrder = orderRepo.findById(id);
    return optTacoOrder.map(tacoOrder -> new ResponseEntity<>(tacoOrder, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @PostMapping(consumes="application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrder(@RequestBody TacoOrder order) {
    return orderRepo.save(order);
  }

  @PutMapping(path="/{orderId}", consumes="application/json")
  public TacoOrder putOrder(
                        @PathVariable("orderId") Long orderId,
                        @RequestBody TacoOrder order) {
    order.setId(orderId);
    return orderRepo.save(order);
  }

  @PatchMapping(path="/{orderId}", consumes="application/json")
  public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
                          @RequestBody TacoOrder patch) {

    TacoOrder order = orderRepo.findById(orderId).get();
    if (patch.getDeliveryName() != null) {
      order.setDeliveryName(patch.getDeliveryName());
    }
    if (patch.getDeliveryStreet() != null) {
      order.setDeliveryStreet(patch.getDeliveryStreet());
    }
    if (patch.getDeliveryCity() != null) {
      order.setDeliveryCity(patch.getDeliveryCity());
    }
    if (patch.getDeliveryState() != null) {
      order.setDeliveryState(patch.getDeliveryState());
    }
    if (patch.getDeliveryZip() != null) {
      order.setDeliveryZip(patch.getDeliveryZip());
    }
    if (patch.getCcNumber() != null) {
      order.setCcNumber(patch.getCcNumber());
    }
    if (patch.getCcExpiration() != null) {
      order.setCcExpiration(patch.getCcExpiration());
    }
    if (patch.getCcCVV() != null) {
      order.setCcCVV(patch.getCcCVV());
    }
    return orderRepo.save(order);
  }

  @DeleteMapping("/{orderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable("orderId") Long orderId) {
    try {
      orderRepo.deleteById(orderId);
    } catch (EmptyResultDataAccessException e) {}
  }

}