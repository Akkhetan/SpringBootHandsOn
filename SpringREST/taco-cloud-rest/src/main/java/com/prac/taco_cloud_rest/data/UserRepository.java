package com.prac.taco_cloud_rest.data;
import com.prac.taco_cloud_rest.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
