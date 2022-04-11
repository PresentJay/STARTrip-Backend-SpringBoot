package com.startrip.codebase.controller.categoryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.dto.category.UpdateCategoryDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.test.context.support.WithMockUser;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.category.RequestCategoryDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String adminToken;
    private Category category;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @BeforeAll
    public void dataSetUp() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/data.sql"));
        }
        createAdminJwt();
    }

    @AfterAll
    public void dataCleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/scheme.sql"));
        }
    }

    private void createAdminJwt() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("admin@admin.com");
        loginDto.setPassword("1234");

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        adminToken = mvcResult.getResponse().getContentAsString();
    }

    @DisplayName("Step1: Category-Controller UnAuthorized CREATE")
    @Order(1)
    @Test
    void category_save() throws Exception {

        RequestCategoryDto dto = new RequestCategoryDto();
        dto.setCategoryParentId(null);
        dto.setCategoryName("음식점");

        mockMvc.perform(
                        post("/api/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

}
