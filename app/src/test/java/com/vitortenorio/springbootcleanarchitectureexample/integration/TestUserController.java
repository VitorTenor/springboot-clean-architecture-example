package com.vitortenorio.springbootcleanarchitectureexample.integration;

import com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler.Problem;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.output.UserOutput;
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

    @Test
    @Order(5)
    @DisplayName("005 - List Users - Success")
    public void listUsers() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .get(URI)
                .contentType("application/json");

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        var users = this.payloadExtractor.asListOf(UserOutput.class);

        assertThat(users).hasSize(2).allSatisfy(user -> {
            assertThat(user.getUuid()).isNotNull();
            assertThat(user.getName()).isNotNull();
            assertThat(user.getLastName()).isNotNull();
            assertThat(user.getEmail()).isNotNull();
        });
    }

    @Test
    @Order(6)
    @DisplayName("006 - Update User - Success")
    public void updateUser() throws Exception {
        // arrange
        var json =
                """
                    {
                        "uuid": "aaa4c5b2-3009-4ad9-84b8-bc80340c4d61",
                        "name": "Johnn",
                        "lastName": "Doe",
                        "email": "john.doe.update@error.com"
                    }
                """;
        var request = MockMvcRequestBuilders
                .put(URI)
                .contentType("application/json")
                .content(json);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(7)
    @DisplayName("007 - Update User - Error - Email already exists")
    public void updateUserErrorEmailAlreadyExists() throws Exception {
        // arrange
        var json =
                """
                    {
                        "uuid": "aaa4c5b2-3009-4ad9-84b8-bc80340c4d61",
                        "name": "Johnn",
                        "lastName": "Doe",
                        "email": "john.doe@error.com"
                    }
                """;
        var request = MockMvcRequestBuilders
                .put(URI)
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
    @Order(8)
    @DisplayName("008 - Update User - Error - User not found")
    public void updateUserErrorUserNotFound() throws Exception {
        // arrange
        var json =
                """
                    {
                        "uuid": "aaa4c5b2-3009-4ad9-84b8-bc80340c4d69",
                        "name": "Johnn",
                        "lastName": "Doe"
                    }
                """;

        var request = MockMvcRequestBuilders
                .put(URI)
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
        assertEquals("User not found", error.getDetail());
        assertEquals("User not found", error.getUserMessage());
    }

    @Test
    @Order(9)
    @DisplayName("009 - Update User - Error - Fields required")
    public void updateUserErrorInvalidFields() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .put(URI)
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
                hasSize(1)
                .allSatisfy(object -> {
                    assertThat(object.getName()).isIn("uuid");
                    assertThat(object.getUserMessage()).isIn("UUID is required");
                });
    }

    @Test
    @Order(10)
    @DisplayName("010 - Get User By UUID - Success")
    public void getUserByUUID() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .get(URI + "/aaa4c5b2-3009-4ad9-84b8-bc80340c4d61")
                .contentType("application/json");

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        var user = this.payloadExtractor.as(UserOutput.class);

        assertThat(user.getUuid()).isNotNull();
        assertThat(user.getName()).isNotNull();
        assertThat(user.getLastName()).isNotNull();
        assertThat(user.getEmail()).isNotNull();
    }

    @Test
    @Order(11)
    @DisplayName("011 - Get User By UUID - Error - User not found")
    public void getUserByUUIDErrorUserNotFound() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .get(URI + "/aaa4c5b2-3009-4ad9-84b8-bc80340c4d69")
                .contentType("application/json");

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());

        var error = this.payloadExtractor.as(Problem.class);

        assertEquals(ProblemType.USER_ERROR.uri(), error.getUri());
        assertEquals(ProblemType.USER_ERROR.title(), error.getTitle());
        assertEquals("User not found", error.getDetail());
        assertEquals("User not found", error.getUserMessage());
    }

    @Test
    @Order(12)
    @DisplayName("012 - Update User Password - Success")
    public void updateUserPassword() throws Exception {
        // arrange
        var json =
                """
                   {
                         "password": "123456",
                         "newPassword": "1234567",
                         "email": "john.doe@error.com"
                   }
                """;
        var request = MockMvcRequestBuilders
                .put(URI + "/password")
                .contentType("application/json")
                .content(json);

        // act
        var resultActions = this.mockMvc.perform(request);
        resultActions.andDo(this.payloadExtractor).andReturn();

        // assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(13)
    @DisplayName("013 - Update User Password - Error - User not found")
    public void updateUserPasswordErrorUserNotFound() throws Exception {
        // arrange
        var json =
                """
                           {
                                 "password": "123456",
                                 "newPassword": "1234567",
                                 "email": "aaaa@aaa.com"
                          }
                        """;

        var request = MockMvcRequestBuilders
                .put(URI + "/password")
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
        assertEquals("User not found", error.getDetail());
        assertEquals("User not found", error.getUserMessage());
    }

    @Test
    @Order(14)
    @DisplayName("014 - Update User Password - Error - Invalid password")
    public void updateUserPasswordErrorInvalidPassword() throws Exception {
        // arrange
        var json =
                """
                   {
                         "password": "1234568",
                         "newPassword": "1234567",
                         "email": "john.doe@error.com"
                   }
                """;
        var request = MockMvcRequestBuilders
                .put(URI + "/password")
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
        assertEquals("Password does not match", error.getDetail());
        assertEquals("Password does not match", error.getUserMessage());
    }

    @Test
    @Order(15)
    @DisplayName("015 - Update User Password - Error - Fields required")
    public void updateUserPasswordErrorInvalidFields() throws Exception {
        // arrange
        var request = MockMvcRequestBuilders
                .put(URI + "/password")
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
                hasSize(3)
                .allSatisfy(object -> {
                    assertThat(object.getName()).isIn("password", "newPassword", "email");
                    assertThat(object.getUserMessage()).isIn("Password is required", "New password is required", "Email is required");
                });
    }

    @Test
    @Order(16)
    @DisplayName("016 - Update User Password - Error - Field Email is invalid")
    public void updateUserPasswordErrorInvalidEmail() throws Exception {
        // arrange
        var json =
                """
                           {
                                 "password": "123456",
                                 "newPassword": "1234567",
                                 "email": "john.doe"
                           }
                        """;
        var request = MockMvcRequestBuilders
                .put(URI + "/password")
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
                    assertThat(object.getUserMessage()).isEqualTo("Email is invalid");
                });
    }
}
