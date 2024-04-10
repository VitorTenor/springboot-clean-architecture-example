package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;
import java.util.UUID;

@Named
public class FindUserByUUIDUseCase {
    private final UserGateway userGateway;

    public FindUserByUUIDUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserEntity execute(UUID uuid) {
        return userGateway.findByUUID(uuid);
    }
}
