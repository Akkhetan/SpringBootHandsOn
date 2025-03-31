package com.prac.taco_cloud_rest.security;

import com.prac.taco_cloud_rest.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

  private String username;
  private String password;
  private String fullname;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String phone;
  private String authority;
  
  public User toUser(PasswordEncoder passwordEncoder) {
    authority = "ROLE_USER";
    return new User(
        username, passwordEncoder.encode(password), authority,
        fullname, street, city, state, zip, phone);
  }
  
}