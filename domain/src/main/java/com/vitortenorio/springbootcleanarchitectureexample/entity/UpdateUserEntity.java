package com.vitortenorio.springbootcleanarchitectureexample.entity;

import java.util.UUID;

public record UpdateUserEntity(
        UUID uuid,
        String name,
        String lastName,
        String email
) {
}
