package com.vitortenorio.springbootcleanarchitectureexample.unit.api.v1.output;

import com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler.Problem;
import com.vitortenorio.springbootcleanarchitectureexample.util.AbstractContextTest;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserOutput Unit Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserOutput extends AbstractContextTest {

    @Test
    @Order(1)
    @DisplayName("001 - Test Getters and Setters")
    public void testGettersAndSetters() {
        // arrange
        Problem problem = new Problem();
        var objectProblem = Problem.Object
                .builder()
                .name("Test Name")
                .userMessage("Test User Message")
                .build();

        // act
        problem.setTitle("Test Title");
        problem.setDetail("Test Detail");
        problem.setStatus(400);
        problem.setUserMessage("Test User Message");
        problem.setTimestamp(LocalDateTime.now());
        problem.setUri("Test URI");
        problem.setObjects(Collections.singletonList(objectProblem));

        // assert
        assertEquals("Test Title", problem.getTitle());
        assertEquals("Test Detail", problem.getDetail());
        assertEquals(400, problem.getStatus());
        assertEquals("Test User Message", problem.getUserMessage());
        assertEquals("Test URI", problem.getUri());
        assertEquals("Test Name", objectProblem.getName());
        assertEquals("Test User Message", objectProblem.getUserMessage());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Test Constructor and Builder")
    public void testProblemConstructorBuilder() {
        // arrange
        var title = "Test Title";
        var detail = "Test Detail";
        var status = 400;
        var userMessage = "Test User Message";
        var timestamp = LocalDateTime.now();
        var uri = "Test URI";
        var objectProblem = Problem.Object
                .builder()
                .name("Test Name")
                .userMessage("Test User Message")
                .build();

        var objects = Collections.singletonList(objectProblem);

        // act
        var problem1 = new Problem(title, detail, status, userMessage, timestamp, uri, objects);
        var problem2 = Problem.builder()
                .title(title)
                .detail(detail)
                .status(status)
                .userMessage(userMessage)
                .timestamp(timestamp)
                .uri(uri)
                .objects(objects)
                .build();

        // assert
        assertEquals(problem1.getTitle(), problem2.getTitle());
        assertEquals(problem1.getDetail(), problem2.getDetail());
        assertEquals(problem1.getStatus(), problem2.getStatus());
        assertEquals(problem1.getUserMessage(), problem2.getUserMessage());
        assertEquals(problem1.getTimestamp(), problem2.getTimestamp());
        assertEquals(problem1.getUri(), problem2.getUri());
        assertEquals(problem1.getObjects(), problem2.getObjects());
    }
}
