package com.vitortenorio.springbootcleanarchitectureexample.unit.api.v1.model;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.model.UserModel;
import com.vitortenorio.springbootcleanarchitectureexample.util.AbstractContextTest;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserModel Unit Tests")
public class TestUserModel extends AbstractContextTest {

    @Test
    @Order(1)
    @DisplayName("001 - Test Getters and Setters")
    public void testUserModelGettersSetters() {
        // arrange
        var userModel = new UserModel();
        var userModel2 = new UserModel();

        // act
        userModel.setUuid(UUID.randomUUID());
        userModel.setName("John");
        userModel.setLastName("Doe");
        userModel.setEmail("john.doe@email.com");
        userModel.setPassword("password");
        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setUpdatedAt(LocalDateTime.now());

        userModel2.setUuid(userModel.getUuid());
        userModel2.setName(userModel.getName());
        userModel2.setLastName(userModel.getLastName());
        userModel2.setEmail(userModel.getEmail());
        userModel2.setPassword(userModel.getPassword());
        userModel2.setCreatedAt(userModel.getCreatedAt());
        userModel2.setUpdatedAt(userModel.getUpdatedAt());

        // assert
        assertEquals(userModel.getUuid(), userModel2.getUuid());
        assertEquals(userModel.getName(), userModel2.getName());
        assertEquals(userModel.getLastName(), userModel2.getLastName());
        assertEquals(userModel.getEmail(), userModel2.getEmail());
        assertEquals(userModel.getPassword(), userModel2.getPassword());
        assertEquals(userModel.getCreatedAt(), userModel2.getCreatedAt());
        assertEquals(userModel.getUpdatedAt(), userModel2.getUpdatedAt());
    }
}
