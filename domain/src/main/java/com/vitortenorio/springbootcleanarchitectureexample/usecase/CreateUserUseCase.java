package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;

@Named
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(CreateUserEntity entity) {
        userGateway.createUser(entity);
    }
}
