package com.vitortenorio.springbootcleanarchitectureexample.api.v1.input;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserPasswordEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserPasswordInput(
        @Email(message = "Email is invalid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "New password is required")
        String newPassword
) {
        public UpdateUserPasswordEntity toEntity() {
                return new UpdateUserPasswordEntity(email, password, newPassword);
        }
}
