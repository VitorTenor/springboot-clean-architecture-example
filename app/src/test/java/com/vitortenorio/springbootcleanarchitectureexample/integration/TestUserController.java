package com.vitortenorio.springbootcleanarchitectureexample.integration;

import com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler.Problem;
import com.vitortenorio.springbootcleanarchitectureexample.enums.ProblemType;
import com.vitortenorio.springbootcleanarchitectureexample.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(
        scripts = {
                "classpath:/db/sql/delete.sql",
                "classpath:/db/sql/insert_user.sql"
        }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("User Controller Integration Test")
public class TestUserController extends AbstractContextTest {
    private final String URI = "/v1/user";

    @Test
    @Order(1)
    @DisplayName("001 - Create User - Success")
    public void createUser() throws Exception {
        // arrange
        var json =
                """
                {
                    "name": "John",
                    "lastName": "Doe",
                    "email": "john.doe@john.com",
                    "password": "123456"
                }
                """;

        var request = MockMvcRequestBuilders
                .post(URI)
                .contentType("application/json")
                .content(json);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Create User - Error - Email already exists")
    public void createUserErrorEmailAlreadyExists() throws Exception {
        // arrange
        var json =
                """
                        {
                            "name": "John",
                            "lastName": "Doe",
                            "email": "john.doe@error.com",
                            "password": "123456"
                        }
                        """;

        var request = MockMvcRequestBuilders
                .post(URI)
                .contentType("application/json")
                .content(json);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertEquals(ProblemType.USER_ERROR.uri(), error.getUri());
        assertEquals(ProblemType.USER_ERROR.title(), error.getTitle());
        assertEquals("Email already exists", error.getDetail());
        assertEquals("Email already exists", error.getUserMessage());
    }

    @Test
    @Order(3)
    @DisplayName("003 - Create User - Error - Fields required")
    public void createUserErrorInvalidFields() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .post(URI)
                .contentType("application/json")
                .content("{}");

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertEquals(ProblemType.INVALID_FIELD.uri(), error.getUri());
        assertEquals(ProblemType.INVALID_FIELD.title(), error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertThat(error.getObjects()).
                hasSize(4)
                .allSatisfy(object -> {
                    assertThat(object.getName()).isIn("name", "lastName", "email", "password");
                    assertThat(object.getUserMessage()).isIn("Name is required", "Last name is required", "Email is required", "Password is required");
                });
    }

    @Test
    @Order(4)
    @DisplayName("004 - Create User - Error - Invalid email")
    public void createUserErrorInvalidEmail() throws Exception {
        // arrange
        var json =
                """
                        {
                            "name": "John",
                            "lastName": "Doe",
                            "email": "john.doe",
                            "password": "123456"
                        }
                        """;

        var request = MockMvcRequestBuilders
                .post(URI)
                .contentType("application/json")
                .content(json);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertEquals(ProblemType.INVALID_FIELD.uri(), error.getUri());
        assertEquals(ProblemType.INVALID_FIELD.title(), error.getTitle());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getDetail());
        assertEquals(ONE_OR_MORE_FIELDS_ARE_INVALID, error.getUserMessage());
        assertThat(error.getObjects()).
                hasSize(1)
                .allSatisfy(object -> {
                    assertThat(object.getName()).isEqualTo("email");
                    assertThat(object.getUserMessage()).isEqualTo("Invalid email");
                });
    }
}
