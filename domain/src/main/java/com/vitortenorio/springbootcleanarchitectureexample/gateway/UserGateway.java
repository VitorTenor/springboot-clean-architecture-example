package com.vitortenorio.springbootcleanarchitectureexample.gateway;

import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserPasswordEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserGateway {
    void create(CreateUserEntity entity);
    List<UserEntity> findAll();
    void update(UpdateUserEntity entity);
    UserEntity findByUUID(final UUID uuid);
    void updatePassword(UpdateUserPasswordEntity entity);
    void delete(UUID uuid);
}
