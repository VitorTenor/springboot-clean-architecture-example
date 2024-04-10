package com.vitortenorio.springbootcleanarchitectureexample.api.v1.controller;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.input.CreateUserInput;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.openapi.UserControllerOpenApi;
import com.vitortenorio.springbootcleanarchitectureexample.usecase.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController implements UserControllerOpenApi {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid CreateUserInput input) {
        createUserUseCase.execute(input.toEntity());
    }

}
