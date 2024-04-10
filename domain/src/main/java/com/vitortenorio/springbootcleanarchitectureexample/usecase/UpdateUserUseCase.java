package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;

@Named
public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UpdateUserEntity entity) {
        userGateway.update(entity);
    }
}
