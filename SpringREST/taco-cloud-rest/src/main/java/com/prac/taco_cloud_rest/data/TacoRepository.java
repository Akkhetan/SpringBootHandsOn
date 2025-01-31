package com.prac.taco_cloud_rest.data;

import com.prac.taco_cloud_rest.entity.Taco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TacoRepository
         extends JpaRepository<Taco, Long> {

}
