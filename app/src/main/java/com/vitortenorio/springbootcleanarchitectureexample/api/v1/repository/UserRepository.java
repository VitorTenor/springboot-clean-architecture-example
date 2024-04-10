package com.vitortenorio.springbootcleanarchitectureexample.api.v1.repository;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Boolean existsByEmailIgnoreCase(String email);
}
