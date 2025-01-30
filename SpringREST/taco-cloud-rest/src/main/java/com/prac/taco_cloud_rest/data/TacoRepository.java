package com.prac.taco_cloud_rest.data;

import com.prac.taco_cloud_rest.domain.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface TacoRepository 
         extends JpaRepository<Taco, Long> {

}
