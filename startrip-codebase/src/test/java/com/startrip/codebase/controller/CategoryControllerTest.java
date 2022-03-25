package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
 public class CategoryControllerTest {

 @Autowired
 private MockMvc mockMvc;

 @Autowired
 private ObjectMapper objectMapper;

 private final CategoryService categoryService;

 @Autowired
 private CategoryRepository categoryRepository;

 @Autowired
 public CategoryControllerTest(CategoryService categoryService) {
  this.categoryService = categoryService;
 }

 //한글 깨짐 해결
 @BeforeEach
 public void before() {
  mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService))
          .addFilters(new CharacterEncodingFilter("UTF-8", true))
          .alwaysExpect(status().isOk())
          .build();
 }

 @DisplayName("Step1: CREATE")
 @Order(1)
 @Test
 void test1() throws Exception {
  String jsonData = "{\"categoryName\" : \"맛집\", "
          + "\"categoryParentId\" : null }";
  mockMvc.perform(post("/api/categories")
          .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
          .content(jsonData)
  ).andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step2: GET(All View)")
 @Order(2)
 @Test
 void test2() throws Exception {
  mockMvc.perform(get("/api/categories")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step3: GET(Detail View)")
 @Order(3)
 @Test
 void test3() throws Exception {
  mockMvc.perform(get("/api/categoreis/1"))
          .andExpect(status().isOk())
          .andDo(print());
 }

 @DisplayName("Step4: UPDATE")
 @Order(4)
 @Test
 void test4() throws Exception {

  String jsonData = "{\"categoryName\" : \"경관\" }";

  mockMvc.perform(patch("/api/categories/{1}")
          .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step5: get")
 @Order(5)
 @Test
 void test5() throws Exception {
  mockMvc.perform(get("/api/categories/{1}")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }

 @DisplayName("Step6: remove")
 @Order(6)
 @Test
 void test6() throws Exception {
  mockMvc.perform(delete("/api/categories/{1}")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }

 @DisplayName("Step7: get")
 @Order(7)
 @Test
 void test7() throws Exception {
  mockMvc.perform(get("/api/categories/{1}")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step8: child get") //TODO: 구현해야 함, 자식 id를 어떻게 추출할 것인가
 @Order(8)
 @Test
 void test8() throws Exception {
  /* mockMvc.perform(get("/api/categories/{...}")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }*/


 }
}
