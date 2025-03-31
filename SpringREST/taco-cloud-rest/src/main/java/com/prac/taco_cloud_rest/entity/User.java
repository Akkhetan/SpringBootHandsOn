package com.prac.taco_cloud_rest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
@Table(name="CUSTOM_USER")
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom_user_seq")
  @SequenceGenerator(name = "custom_user_seq", sequenceName = "custom_user_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false, unique = true)
  private final String username;
  private final String password;
  private final String authority;

  private final String fullname;
  private final String street;
  private final String city;
  private final String state;
  private final String zip;
  private final String phoneNumber;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return Arrays.stream(Objects.requireNonNull(authority).split(","))
            .map(role -> (GrantedAuthority) () -> role)
            .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


}