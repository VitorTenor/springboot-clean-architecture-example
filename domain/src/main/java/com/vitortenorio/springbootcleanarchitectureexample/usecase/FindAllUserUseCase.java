package com.vitortenorio.springbootcleanarchitectureexample.usecase;

import com.vitortenorio.springbootcleanarchitectureexample.entity.UserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class FindAllUserUseCase {
    private final UserGateway userGateway;

    public FindAllUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<UserEntity> execute() {
        return userGateway.findAll();
    }
}
