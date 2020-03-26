package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignInDto;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<String> signIn(SignInDto signInDto) {
        Optional<User> userCandidate = userRepository.find(signInDto.getEmail());
        return userCandidate.map(user -> user.getId().toString());
    }
}
