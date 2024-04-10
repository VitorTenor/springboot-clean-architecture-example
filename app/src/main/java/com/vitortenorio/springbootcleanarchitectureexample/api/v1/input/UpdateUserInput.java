package com.vitortenorio.springbootcleanarchitectureexample.api.v1.input;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserEntity;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserInput(
        @NotNull(message = "UUID is required")
        UUID uuid,
        String name,
        String lastName,
        String email
) {
        public UpdateUserEntity toEntity() {
                return new UpdateUserEntity(uuid, name, lastName, email);
        }
}
