package com.prac.taco_cloud_jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest()   // <1>
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;   // <2>

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/"))    // <3>
      .andExpect(status().isOk())  // <4>
      .andExpect(view().name("home"))  // <5>
      .andExpect(content().string(           // <6>
          containsString("Welcome to...")));
  }

}
