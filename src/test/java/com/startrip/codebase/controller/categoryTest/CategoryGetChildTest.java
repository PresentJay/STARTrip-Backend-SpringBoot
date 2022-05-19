package com.startrip.codebase.controller.categoryTest;

import com.startrip.codebase.domain.category.Category;
import org.junit.jupiter.api.extension.ExtendWith;
import com.startrip.codebase.domain.category.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
public class CategoryGetChildTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

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
            dataCleanUp();
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/data.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/categoryTestData.sql"));
        }
    }

    @AfterAll
    public void dataCleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/scheme.sql"));
        }
    }


    @DisplayName("Step1: Category-Controller GET-ChildFull Test")
    @Order(1)
    @Test
    void category_get_childFull() throws Exception {

        Category category = categoryRepository.findCategoryByCategoryName("ROOT").get();
        mockMvc.perform(
                        get("/api/category/child-full/" + category.getCategoryId())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Step2: Category-Controller GET-child Test")
    @Order(2)
    @Test
    void category_get_child() throws Exception {
        Category category = categoryRepository.findCategoryByCategoryName("ROOT").get();
        mockMvc.perform(
                        get("/api/category/child-full/" + category.getCategoryId())
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
