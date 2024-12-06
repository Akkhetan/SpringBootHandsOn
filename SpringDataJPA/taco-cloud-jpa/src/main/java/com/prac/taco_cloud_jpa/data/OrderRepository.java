package com.prac.taco_cloud_jpa.data;

import com.prac.taco_cloud_jpa.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
