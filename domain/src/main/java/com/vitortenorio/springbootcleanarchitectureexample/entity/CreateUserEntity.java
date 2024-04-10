package com.vitortenorio.springbootcleanarchitectureexample.entity;

public record CreateUserEntity(
        String name,
        String lastName,
        String email,
        String password
) {
}
