package com.vitortenorio.springbootcleanarchitectureexample.entity;

public record UpdateUserPasswordEntity (
    String email,
    String password,
    String newPassword
){
}
