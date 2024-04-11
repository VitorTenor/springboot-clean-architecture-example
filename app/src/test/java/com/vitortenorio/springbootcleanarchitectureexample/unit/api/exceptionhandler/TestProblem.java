package com.vitortenorio.springbootcleanarchitectureexample.unit.api.exceptionhandler;

import com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler.Problem;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problem Unit Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestProblem {

    @Test
    @Order(1)
    @DisplayName("001 - Test Getters and Setters")
    public void testProblemGettersSetters() {
        // arrange
        var problem = new Problem();
        var problem2 = new Problem();

        // act
        problem.setStatus(400);
        problem.setTitle("Bad Request");
        problem.setDetail("Invalid request");

        problem2.setStatus(problem.getStatus());
        problem2.setTitle(problem.getTitle());
        problem2.setDetail(problem.getDetail());

        // assert
        assertEquals(problem.getStatus(), problem2.getStatus());
        assertEquals(problem.getTitle(), problem2.getTitle());
        assertEquals(problem.getDetail(), problem2.getDetail());
    }

}

