package ru.itis.services;

import ru.itis.dto.SignInDto;

import java.util.Optional;

public interface SignInService {
    public Optional<String> signIn(SignInDto signInDto);
}
