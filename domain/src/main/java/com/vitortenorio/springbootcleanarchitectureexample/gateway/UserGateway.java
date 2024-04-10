package com.vitortenorio.springbootcleanarchitectureexample.gateway;

import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;

public interface UserGateway {
    void createUser(CreateUserEntity entity);
}
