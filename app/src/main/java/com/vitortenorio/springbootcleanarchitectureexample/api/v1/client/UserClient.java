package com.vitortenorio.springbootcleanarchitectureexample.api.v1.client;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.model.UserModel;
import com.vitortenorio.springbootcleanarchitectureexample.api.v1.repository.UserRepository;
import com.vitortenorio.springbootcleanarchitectureexample.core.security.PasswordUtil;
import com.vitortenorio.springbootcleanarchitectureexample.core.util.MessageHelper;
import com.vitortenorio.springbootcleanarchitectureexample.entity.CreateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UpdateUserPasswordEntity;
import com.vitortenorio.springbootcleanarchitectureexample.entity.UserEntity;
import com.vitortenorio.springbootcleanarchitectureexample.exception.UserException;
import com.vitortenorio.springbootcleanarchitectureexample.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserClient implements UserGateway {
    private final UserRepository userRepository;
    private final MessageHelper messageHelper;

    @Override
    @Transactional
    public void create(CreateUserEntity entity) {
        if (Boolean.TRUE.equals(this.emailExists(entity.email()))) {
            throw new UserException(messageHelper.getMessage("email.already.exists"));
        }

        userRepository.save(
                this.toModel(entity)
        );
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll()
                .stream().map(this::toEntity)
                .toList();
    }

    @Override
    @Transactional
    public void update(UpdateUserEntity entity) {
        final var emailExists = this.emailExistsAndUuidNot(entity.email(), entity.uuid());
        if (Boolean.TRUE.equals(emailExists)) {
            throw new UserException(messageHelper.getMessage("email.already.exists"));
        }

        var model = userRepository.findByUuid(entity.uuid())
                .orElseThrow(() -> new UserException(messageHelper.getMessage("user.not.found")));

        this.updateModel(model, entity);

        userRepository.save(model);
    }

    @Override
    public UserEntity findByUUID(final UUID uuid) {
        return userRepository.findByUuid(uuid)
                .map(this::toEntity)
                .orElseThrow(() -> new UserException(messageHelper.getMessage("user.not.found")));
    }

    @Override
    @Transactional
    public void updatePassword(UpdateUserPasswordEntity entity) {
        var model = userRepository.findByEmail(entity.email())
                .orElseThrow(() -> new UserException(messageHelper.getMessage("user.not.found")));

        final var isPasswordMatch = PasswordUtil.matches(entity.password(), model.getPassword());
        if (Boolean.FALSE.equals(isPasswordMatch)) {
            throw new UserException(messageHelper.getMessage("password.does.not.match"));
        }

        final var password = PasswordUtil.encode(entity.newPassword());
        model.setPassword(password);

        userRepository.save(model);
    }

    private Boolean emailExistsAndUuidNot(final String email, final UUID uuid) {
        return userRepository.existsByEmailAndUuidNot(email, uuid);
    }

    private Boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    private void updateModel(UserModel model, UpdateUserEntity entity) {
        model.setName(
                Objects.nonNull(entity.name()) ? entity.name() : model.getName()
        );
        model.setLastName(
                Objects.nonNull(entity.lastName()) ? entity.lastName() : model.getLastName()
        );
        model.setEmail(
                Objects.nonNull(entity.email()) ? entity.email() : model.getEmail()
        );
        model.setUpdatedAt(LocalDateTime.now());
    }

    private UserEntity toEntity(final UserModel model) {
        return new UserEntity(
                model.getUuid(), model.getName(),
                model.getLastName(), model.getEmail()
        );
    }

    private UserModel toModel(CreateUserEntity entity) {
        var userModel = new UserModel();

        userModel.setName(entity.name());
        userModel.setLastName(entity.lastName());
        userModel.setEmail(entity.email());

        final var password = PasswordUtil.encode(entity.password());
        userModel.setPassword(password);

        final var date = LocalDateTime.now();
        userModel.setCreatedAt(date);
        userModel.setUpdatedAt(date);

        return userModel;
    }
}
