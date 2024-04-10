package com.vitortenorio.springbootcleanarchitectureexample.api.v1.controller;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.assembler.UserAssembler;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.input.CreateUserInput;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.input.UpdateUserInput;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.input.UpdateUserPasswordInput;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.openapi.UserControllerOpenApi;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.output.UserOutput;
import com.vitortenorio.springbootcleanarchitectureexample.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController implements UserControllerOpenApi {
    private final FindAllUserUseCase findAllUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindUserByUUIDUseCase findUserByUUIDUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    private final UserAssembler userAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CreateUserInput input) {
        createUserUseCase.execute(input.toEntity());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutput> findAll() {
        return userAssembler.toOutputList(findAllUserUseCase.execute());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid UpdateUserInput input) {
        updateUserUseCase.execute(input.toEntity());
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput findByUUID(@PathVariable final UUID uuid) {
        return userAssembler.toOutput(
                findUserByUUIDUseCase.execute(uuid)
        );
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody @Valid UpdateUserPasswordInput input) {
        updateUserPasswordUseCase.execute(input.toEntity());
    }
}
