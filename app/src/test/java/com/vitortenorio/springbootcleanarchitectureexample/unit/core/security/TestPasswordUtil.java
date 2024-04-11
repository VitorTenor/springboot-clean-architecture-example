package com.vitortenorio.springbootcleanarchitectureexample.unit.core.security;

import com.vitortenorio.springbootcleanarchitectureexample.core.security.PasswordUtil;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordUtil Unit Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPasswordUtil {

    @Test
    @Order(1)
    @DisplayName("001 - Test Encode")
    public void testEncode() {
        // arrange
        var password = "password";

        // act
        var encodedPassword = PasswordUtil.encode(password);

        // assert
        assertNotNull(encodedPassword);
        assertNotEquals(password, encodedPassword);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test Matches - True")
    public void testMatches() {
        // arrange
        var password = "password";
        var encodedPassword = PasswordUtil.encode(password);

        // act
        var matches = PasswordUtil.matches(password, encodedPassword);

        // assert
        assertTrue(matches);
    }

    @Test
    @Order(3)
    @DisplayName("003 - Test Matches - False")
    public void testMatchesFalse() {
        // arrange
        var password = "password";
        var encodedPassword = PasswordUtil.encode(password);

        // act
        var matches = PasswordUtil.matches("wrongPassword", encodedPassword);

        // assert
        assertFalse(matches);
    }

}
