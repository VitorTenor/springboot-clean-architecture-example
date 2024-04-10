package com.vitortenorio.springbootcleanarchitectureexample.api.v1.input;

import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserInput (
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Last name is required")
        String lastName,
        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password
) {
    public CreateUserEntity toEntity() {
        return new CreateUserEntity(name, lastName, email, password);
    }
}

