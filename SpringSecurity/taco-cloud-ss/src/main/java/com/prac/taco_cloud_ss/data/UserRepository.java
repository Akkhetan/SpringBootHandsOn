package com.prac.taco_cloud_ss.data;
import com.prac.taco_cloud_ss.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
