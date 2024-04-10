package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserPasswordEntity;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;

@Named
public class UpdateUserPasswordUseCase {
    private final UserGateway userGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UpdateUserPasswordEntity entity) {
        userGateway.updatePassword(entity);
    }
}
