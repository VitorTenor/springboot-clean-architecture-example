package com.vitortenorio.springbootcleanarchitectureexample.api.v1.client;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.model.UserModel;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.repository.UserRepository;
import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.exception.UserException;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserClient implements UserGateway {
    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserEntity entity) {
        if (Boolean.TRUE.equals(this.emailExists(entity.email()))) {
            throw new UserException("Email already exists");
        }

        userRepository.save(
                this.toModel(entity)
        );
    }

    private Boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    private UserModel toModel(CreateUserEntity entity) {
        var userModel = new UserModel();

        userModel.setName(entity.name());
        userModel.setLastName(entity.lastName());
        userModel.setEmail(entity.email());

        final var password = new BCryptPasswordEncoder().encode(entity.password());
        userModel.setPassword(password);

        final var date = LocalDateTime.now();
        userModel.setCreatedAt(date);
        userModel.setUpdatedAt(date);

        return userModel;
    }
}
