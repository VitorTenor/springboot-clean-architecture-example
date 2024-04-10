package com.vitortenorio.springbootcleanarchitectureexample.api.v1.repository;

import com.vitortenorio.springbootcleanarchitectureexample.api.v1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Boolean existsByEmailIgnoreCase(String email);

    Optional<UserModel> findByUuid(UUID uuid);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserModel u WHERE u.email = :email AND u.uuid != :uuid")
    Boolean existsByEmailAndUuidNot(@Param("email") String email, @Param("uuid") UUID uuid);

    Optional<UserModel> findByEmail(String email);
}
