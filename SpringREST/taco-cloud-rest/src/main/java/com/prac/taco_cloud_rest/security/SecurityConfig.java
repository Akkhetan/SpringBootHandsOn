package com.prac.taco_cloud_rest.security;

import com.prac.taco_cloud_rest.data.UserRepository;
import com.prac.taco_cloud_rest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig   {

    /*
         # Used when creating new users and when authenticating users at login. In this case, we’re using BCryptPasswordEncoder,
         one of a handful of password encoders provided by Spring
        # No matter which password encoder you use, it’s important to understand that the password in the database is never decoded. Instead,
        the password that the user enters at login is encoded using the same algorithm, and it’s then compared with the encoded password in the database.
        That comparison is performed in the PasswordEncoder’s matches() method.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //configure a user store for authentication purposes
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) {
                log.info("User : {}", user);
                List<String> list = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                log.info(" Granted Authorities : {}, Length : {}", list, list.size());
                return user;
            }

            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }


    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults()); //Use HTTP Basic authentication approach


        // Authorization filter
        http.authorizeHttpRequests(c -> c
                        .requestMatchers("/design/**","/orders/**","/api/orders/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tacos/**").hasRole("ADMIN")
                        .requestMatchers("/api/tacos/**").authenticated()
                        .anyRequest().permitAll());

        //custom login page
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/design", true));

        http.logout( logout -> logout.logoutSuccessUrl("/login"));


        http.addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class); //Added custom filter


        /*http.csrf(c -> c.disable()); */  //Disables CSRF to enable a call to the /a path using the HTTP POST method

        // disable csrf protection against particular end point matchers
        http.csrf(c -> c.ignoringRequestMatchers("/api/tacos/**"));

        return http.build();
    }
}