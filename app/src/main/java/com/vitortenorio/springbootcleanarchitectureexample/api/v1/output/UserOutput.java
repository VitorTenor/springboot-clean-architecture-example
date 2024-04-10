package com.vitortenorio.springbootcleanarchitectureexample.api.v1.output;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserOutput {
    private final UUID uuid;
    private final String name;
    private final String lastName;
    private final String email;
}
