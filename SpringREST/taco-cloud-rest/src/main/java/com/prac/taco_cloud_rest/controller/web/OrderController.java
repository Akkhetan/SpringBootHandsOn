package com.prac.taco_cloud_rest.controller.web;

import com.prac.taco_cloud_rest.data.OrderRepository;
import com.prac.taco_cloud_rest.entity.TacoOrder;
import com.prac.taco_cloud_rest.entity.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

  private OrderRepository orderRepo;

  public OrderController(OrderRepository orderRepo) {
    this.orderRepo = orderRepo;
  }

  @GetMapping("/current")
  public String orderForm() {
    return "orderForm";
  }


  @PostMapping
  public String processOrder(@Valid TacoOrder order, Errors errors,
                             SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    order.setUser(user);

    orderRepo.save(order);
    log.info("Order submitted: {}", order);
    sessionStatus.setComplete(); /* The TacoOrder object was initially created and placed into the session when the user created their first taco.
    By calling setComplete(), we are ensuring that the session is cleaned up and ready for a new order the next time the user creates a taco.*/

    return "redirect:/design";
  }
}