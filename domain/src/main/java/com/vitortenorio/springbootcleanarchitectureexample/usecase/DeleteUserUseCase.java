package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;
import java.util.UUID;

@Named
public class DeleteUserUseCase {
    private final UserGateway userGateway;

    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(final UUID uuid) {
        userGateway.delete(uuid);
    }
}
