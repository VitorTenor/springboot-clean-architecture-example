package com.vitortenorio.springbootcleanarchitectureexample.api.exceptionhandler;

import com.vitortenorio.springbootcleanarchitectureexample.enums.ProblemType;
import com.vitortenorio.springbootcleanarchitectureexample.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private final String INVALID_FIELD = "One or more fields are invalid. Fill in correctly and try again.";

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, headers, HttpStatus.valueOf(status.value()), request, ex.getBindingResult());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex, WebRequest request) {
        final var problemType = ProblemType.USER_ERROR;

        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title(problemType.title())
                .detail(ex.getMessage())
                .userMessage(ex.getMessage())
                .uri(problemType.uri())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleValidationInternal(
            Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        final var problemType = ProblemType.INVALID_FIELD;

        var problem = Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(problemType.title())
                .detail(INVALID_FIELD)
                .userMessage(INVALID_FIELD)
                .uri(problemType.uri())
                .objects(buildObjectList(bindingResult))
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private List<Problem.Object> buildObjectList(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    final var message = objectError.getDefaultMessage();
                    var name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .toList();
    }
}
