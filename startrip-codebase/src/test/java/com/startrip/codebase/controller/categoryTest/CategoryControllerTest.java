package com.startrip.codebase.controller.categoryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.controller.CategoryController;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.category.dto.CreateCategoryDto;
import com.startrip.codebase.domain.category.dto.UpdateCategoryDto;
import com.startrip.codebase.service.CategoryService;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
          .build();
 }

 @DisplayName("Step1: CREATE")
 @Order(1)
 @Test
 void test1() throws Exception {

  CreateCategoryDto dto = new CreateCategoryDto();
  dto.setCategoryName("맛집");
  dto.setCategoryParentId(null);


  mockMvc.perform(post("/api/category")
          .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
          .content(objectMapper.writeValueAsString(dto))
          )
          .andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step2: GET(All View)")
 @Order(2)
 @Test
 void test2() throws Exception {
  mockMvc.perform(get("/api/category")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }


 @DisplayName("Step3: GET(Detail View)")
 @Order(3)
 @Test
 void test3() throws Exception {

  String root = "ROOT";
  String market = "맛집";

  //자동 생성된 (id:1) Root Category 확인
  mockMvc.perform(get("/api/category/1"))
          .andExpect(status().isOk())
          .andDo(print());

  // step1에서 생성된 (id:2)
  mockMvc.perform(get("/api/category/2"))
          //.andExpect(model().attribute("categoryName", "맛집"))
          //.andExpect((ResultMatcher) content().string(market))
          .andExpect(status().isOk())
          .andDo(print());
 }

 @DisplayName("Step4: UPDATE")
 @Order(4)
 @Test
 void test4() throws Exception {

  UpdateCategoryDto dto = new UpdateCategoryDto();
  dto.setId((long)2);
  dto.setCategoryName("관광");

  mockMvc.perform(put("/api/category/2")
          .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
          .content(objectMapper.writeValueAsString(dto))
          )
          .andExpect(status().isOk())
          .andDo(print());
 }


 @DisplayName("Step5: get")
 @Order(5)
 @Test
 void test5() throws Exception {
  mockMvc.perform(get("/api/category/2")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }

 @DisplayName("Step6: remove")
 @Order(6)
 @Test
 void test6() throws Exception {
  mockMvc.perform(delete("/api/category/2")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isOk()).andDo(print());
 }

 @DisplayName("Step7: get")
 @Order(7)
 @Test
 void test7() throws Exception {
  mockMvc.perform(get("/api/category/2")
          .accept(MediaType.APPLICATION_JSON)
  ).andExpect(status().isBadRequest()).andDo(print());
 }

 @DisplayName("Step8: UniquePropertyTest - Create CategoryThatHasRemovedName")
 @Order(8)
 @Test
 void test8() throws Exception {
  CreateCategoryDto dto = new CreateCategoryDto();
  dto.setCategoryName("맛집");
  dto.setCategoryParentId((long)1);

  mockMvc.perform(post("/api/category")
                  .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
                  .content(objectMapper.writeValueAsString(dto))
          )
          .andExpect(status().isOk()).andDo(print());
 }
}
