package com.prac.taco_cloud_rest.data;

import com.prac.taco_cloud_rest.entity.TacoOrder;
import com.prac.taco_cloud_rest.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface OrderRepository 
         extends CrudRepository<TacoOrder, Long> {

  List<TacoOrder> findByUserOrderByPlacedAtDesc(
          User user, Pageable pageable);

}
